package com.benbenlaw.tiab_charger.block.entity;

import com.benbenlaw.core.block.entity.SyncableBlockEntity;
import com.benbenlaw.tiab_charger.block.TIABChargerBlocks;
import com.benbenlaw.tiab_charger.config.StartupConfig;
import com.benbenlaw.tiab_charger.screen.TIABChargerMenu;
import com.benbenlaw.tiab_charger.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mangorage.tiab.common.items.TiabItem;

public class TIABChargerBlockEntity extends SyncableBlockEntity implements MenuProvider {
    public final ContainerData data;
    private static final int ENERGY_PER_CHARGE = StartupConfig.rfPerCharge.get();
    private static final int SECONDS_PER_CHARGE = StartupConfig.secondsPerCharge.get();

    public TIABChargerBlockEntity(BlockPos pos, BlockState state) {
        super(TIABChargerBlockEntities.TIAB_CHARGER_BLOCK_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(10000000, 1000000) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    public ItemStackHandler getItemStackHandler() {
        return itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return TIABChargerBlocks.TIAB_CHARGER.get().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int container, @NotNull Inventory inventory, @NotNull Player player) {
        return new TIABChargerMenu(container, inventory, this.getBlockPos(), data);
    }

    public void tick() {
        if (!level.isClientSide()) {
            sync();
            if (itemHandler.getStackInSlot(0).getItem() instanceof TiabItem tiab) {
                int totalEnergyRequired = ENERGY_PER_CHARGE * SECONDS_PER_CHARGE;

                if (ENERGY_STORAGE.getEnergyStored() >= totalEnergyRequired) {
                    ENERGY_STORAGE.extractEnergy(totalEnergyRequired, false);

                    for (int i = 0; i < SECONDS_PER_CHARGE; i++) {
                        tiab.tickBottle(itemHandler.getStackInSlot(0));
                    }

                    //System.out.println("TIAB Charger charged a TIAB item by " + SECONDS_PER_CHARGE + " seconds.");
                }
            }
        }
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return this.ENERGY_STORAGE;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.put("inventory", this.itemHandler.serializeNBT(provider));
        compoundTag.putInt("energy", ENERGY_STORAGE.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        this.itemHandler.deserializeNBT(provider, compoundTag.getCompound("inventory"));
        this.ENERGY_STORAGE.setEnergy(compoundTag.getInt("energy"));
        super.loadAdditional(compoundTag, provider);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }



}
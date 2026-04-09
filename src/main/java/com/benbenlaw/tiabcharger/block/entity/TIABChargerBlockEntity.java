package com.benbenlaw.tiabcharger.block.entity;

import com.benbenlaw.tiabcharger.TIABCharger;
import com.benbenlaw.tiabcharger.block.TIABChargerBlockEntities;
import com.benbenlaw.tiabcharger.block.TIABChargerBlocks;
import com.benbenlaw.tiabcharger.block.custom.TIABChargerBlock;
import com.benbenlaw.tiabcharger.config.StartupConfig;
import com.benbenlaw.tiabcharger.core.InputItemHandler;
import com.benbenlaw.tiabcharger.core.SyncableBlockEntity;
import com.benbenlaw.tiabcharger.screen.TIABChargerMenu;
import com.benbenlaw.tiabcharger.util.EnergyHandler;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandlerUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.mangorage.tiab.common.api.impl.ITiabItem;

public class TIABChargerBlockEntity extends SyncableBlockEntity implements MenuProvider {
    private final ContainerData data;
    private static final int ENERGY_PER_CHARGE = StartupConfig.rfPerCharge.get();
    private static final int SECONDS_PER_CHARGE = StartupConfig.secondsPerCharge.get();

    private final InputItemHandler inputHandler = new InputItemHandler(this, 2,
            (i, stack) -> i == 0 || i == 1);

    private final EnergyHandler energyHandler = new EnergyHandler(1000000, 100000, this);

    public TIABChargerBlockEntity(BlockPos pos, BlockState state) {
        super(TIABChargerBlockEntities.TIAB_CHARGER_BLOCK_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> energyHandler.getAmountAsInt();
                    case 1 -> energyHandler.getCapacityAsInt();
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> energyHandler.getAmountAsInt();
                    case 1 -> energyHandler.getCapacityAsInt();
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }

    public void tick() {
        if (!level.isClientSide()) {

            boolean isRunning = level.getBlockState(worldPosition).getValue(TIABChargerBlock.RUNNING);

            if (!isRunning) {
                updateWorkingState(false);
                return;
            }

            boolean isWorking = false;

            ItemStack stack = ItemUtil.getStack(inputHandler, 0);
            int energyStored = energyHandler.getCapacityAsInt();

            if (energyStored >= ENERGY_PER_CHARGE && stack.getItem() instanceof ITiabItem tiab) {
                isWorking = true;
                for (int i = 0; i < SECONDS_PER_CHARGE; i++) {

                    try (Transaction tx = Transaction.open(null)) {
                        energyHandler.extract(ENERGY_PER_CHARGE, tx);
                        tiab.tickBottle(stack);
                        inputHandler.set(0, ItemResource.of(stack), 1);
                        tx.commit();
                    }
                }
            }

            updateWorkingState(isWorking);

        }
    }

    private void updateWorkingState(boolean working) {
        assert level != null;
        BlockState state = level.getBlockState(worldPosition);
        if (state.getValue(TIABChargerBlock.WORKING) != working) {
            level.setBlock(worldPosition, state.setValue(TIABChargerBlock.WORKING, working), 3);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        inputHandler.serialize(output.child("input"));
        energyHandler.serialize(output.child("energy"));
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        inputHandler.deserialize(input.childOrEmpty("input"));
        energyHandler.deserialize(input.childOrEmpty("energy"));
        super.loadAdditional(input);
    }

    public InputItemHandler getInputHandler() { return inputHandler; }
    public EnergyHandler getEnergyHandler() { return energyHandler; }
    public ResourceHandler<ItemResource> getItemCapability() {
        return inputHandler;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int container, Inventory inventory, Player player) {
        return new TIABChargerMenu(container, inventory, this.worldPosition, data);
    }

    @Override
    public @NonNull Component getDisplayName() {
        return Component.translatable("block.tiabcharger.tiab_charger");
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        dropInventoryContents(inputHandler);
    }


}
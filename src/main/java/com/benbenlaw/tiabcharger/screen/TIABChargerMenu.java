package com.benbenlaw.tiabcharger.screen;

import com.benbenlaw.tiabcharger.block.entity.TIABChargerBlockEntity;
import com.benbenlaw.tiabcharger.core.InputSlot;
import com.benbenlaw.tiabcharger.core.SimpleAbstractContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TIABChargerMenu extends SimpleAbstractContainerMenu {

    protected TIABChargerBlockEntity blockEntity;
    protected Level level;
    protected ContainerData data;
    protected Player player;
    protected BlockPos blockPos;

    public TIABChargerMenu(int containerID, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerID, inventory, extraData.readBlockPos(), new SimpleContainerData(2));
    }

    public TIABChargerMenu(int containerID, Inventory inventory, BlockPos blockPos, ContainerData data) {
        super(TIABMenus.TIAB_CHARGER_MENU.get(), containerID, inventory, blockPos, 1);
        this.player = inventory.player;
        this.blockPos = blockPos;
        this.level = inventory.player.level();
        this.blockEntity = (TIABChargerBlockEntity) this.level.getBlockEntity(blockPos);
        this.data = data;

        assert this.blockEntity != null;
        this.addSlot(new InputSlot(blockEntity.getInputHandler(), blockEntity.getInputHandler()::set, 0, 80, 35));

        addDataSlots(this.data);

    }

    public boolean hasEnergy() {
        return data.get(0) > 0 ;
    }

    public int getEnergyFilled() {

        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 52; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}

package com.benbenlaw.tiabcharger.util;

import com.benbenlaw.tiabcharger.core.SyncableBlockEntity;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;

public class EnergyHandler extends SimpleEnergyHandler {
    private final SyncableBlockEntity blockEntity;

    public EnergyHandler(int capacity, int maxTransfer, SyncableBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
        super(capacity, maxTransfer);
    }

    @Override
    protected void onEnergyChanged(int previousAmount) {
        blockEntity.sync();
        super.onEnergyChanged(previousAmount);
    }
}
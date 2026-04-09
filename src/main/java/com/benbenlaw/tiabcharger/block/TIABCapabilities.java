package com.benbenlaw.tiabcharger.block;

import com.benbenlaw.tiabcharger.block.entity.TIABChargerBlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class TIABCapabilities {


    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, TIABChargerBlockEntities.TIAB_CHARGER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getItemCapability());

        event.registerBlockEntity(Capabilities.Energy.BLOCK, TIABChargerBlockEntities.TIAB_CHARGER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getEnergyHandler());
    }


}

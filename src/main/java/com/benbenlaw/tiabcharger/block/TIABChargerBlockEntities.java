package com.benbenlaw.tiabcharger.block;

import com.benbenlaw.tiabcharger.TIABCharger;
import com.benbenlaw.tiabcharger.block.entity.TIABChargerBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class TIABChargerBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, TIABCharger.MOD_ID);

    public static final Supplier<BlockEntityType<TIABChargerBlockEntity>> TIAB_CHARGER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("tiab_charger_block_entity", () ->
                    new BlockEntityType<>(TIABChargerBlockEntity::new, TIABChargerBlocks.TIAB_CHARGER.get()));
}

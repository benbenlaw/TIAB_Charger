package com.benbenlaw.tiab_charger.block.entity;

import com.benbenlaw.tiab_charger.TIABCharger;
import com.benbenlaw.tiab_charger.block.TIABChargerBlocks;
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

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, TIABCharger.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TIABChargerBlockEntity>> TIAB_CHARGER_BLOCK_ENTITY =
            register("tiab_charger_block_entity", () ->
                    BlockEntityType.Builder.of(TIABChargerBlockEntity::new, TIABChargerBlocks.TIAB_CHARGER.get()));

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,
                TIABChargerBlockEntities.TIAB_CHARGER_BLOCK_ENTITY.get(), TIABChargerBlockEntity::getEnergyStorage);
    }



    public static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(@Nonnull String name, @Nonnull Supplier<BlockEntityType.Builder<T>> initializer) {
        return BLOCK_ENTITIES.register(name, () -> initializer.get().build(null));
    }
}

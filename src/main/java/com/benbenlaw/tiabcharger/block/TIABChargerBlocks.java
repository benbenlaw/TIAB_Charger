package com.benbenlaw.tiabcharger.block;

import com.benbenlaw.tiabcharger.TIABCharger;
import com.benbenlaw.tiabcharger.block.custom.TIABChargerBlock;
import com.benbenlaw.tiabcharger.item.TIABChargerItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class TIABChargerBlocks {

    public static final DeferredRegister.Blocks BLOCKS =DeferredRegister.createBlocks(TIABCharger.MOD_ID);

    public static final DeferredBlock<Block> TIAB_CHARGER = registerBlock("tiab_charger",
            properties -> new TIABChargerBlock(properties
                    .strength(1.0F)
                    .noOcclusion()));



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        TIABChargerItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

}

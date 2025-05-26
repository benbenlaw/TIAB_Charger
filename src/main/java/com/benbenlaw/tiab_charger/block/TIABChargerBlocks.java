package com.benbenlaw.tiab_charger.block;

import com.benbenlaw.tiab_charger.TIABCharger;
import com.benbenlaw.tiab_charger.item.TIABChargerItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TIABChargerBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TIABCharger.MOD_ID);
    public static final DeferredBlock<Block> TIAB_CHARGER = registerBlock("tiab_charger",
            () -> new TIABChargerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        TIABChargerItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));

    }

}

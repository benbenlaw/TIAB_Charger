package com.benbenlaw.tiabcharger.data;

import com.benbenlaw.tiabcharger.block.TIABChargerBlocks;
import com.benbenlaw.tiabcharger.block.custom.TIABChargerBlock;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TIABChargerLootTableProvider extends VanillaBlockLoot {

    public TIABChargerLootTableProvider(HolderLookup.Provider p_344962_) {
        super(p_344962_);
    }
    @Override
    protected void generate() {
        dropSelf(TIABChargerBlocks.TIAB_CHARGER.get());
    }

    @Override
    protected void add(@NotNull Block block, @NotNull LootTable.Builder table) {
        //Overwrite the core register method to add to our list of known blocks
        super.add(block, table);
        knownBlocks.add(block);
    }
    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}

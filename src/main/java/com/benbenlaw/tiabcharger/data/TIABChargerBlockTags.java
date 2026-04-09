package com.benbenlaw.tiabcharger.data;

import com.benbenlaw.tiabcharger.TIABCharger;
import com.benbenlaw.tiabcharger.block.TIABChargerBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;
import org.mangorage.tiab.common.TiabMod;

import java.util.concurrent.CompletableFuture;

public class TIABChargerBlockTags extends BlockTagsProvider {

    TIABChargerBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TIABCharger.MOD_ID);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //Pickaxe
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TIABChargerBlocks.TIAB_CHARGER.get())
        ;

    }

    @Override
    public @NotNull String getName() {
        return TIABCharger.MOD_ID + " Block Tags";
    }
}

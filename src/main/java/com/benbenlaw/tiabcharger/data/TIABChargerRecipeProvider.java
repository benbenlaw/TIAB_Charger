package com.benbenlaw.tiabcharger.data;

import com.benbenlaw.tiabcharger.TIABCharger;
import com.benbenlaw.tiabcharger.block.TIABChargerBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class TIABChargerRecipeProvider extends RecipeProvider {

    public TIABChargerRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }
    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput recipeOutput) {
            return new TIABChargerRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return TIABCharger.MOD_ID + " Recipes";
        }
    }

    @Override
    protected void buildRecipes() {

        //TIAB Charger
        shaped(RecipeCategory.MISC, TIABChargerBlocks.TIAB_CHARGER.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("ABA")
                .define('A', Tags.Items.STORAGE_BLOCKS_GOLD)
                .define('B', Items.CLOCK)
                .define('C', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('D', Items.NETHER_STAR).unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                .save(output);

    }
}

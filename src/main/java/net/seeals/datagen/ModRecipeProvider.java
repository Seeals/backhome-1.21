package net.seeals.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.seeals.registry.ModBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        //(RecipeExporter exporter, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group)
        offerBlasting(exporter, Arrays.asList(ModBlocks.BRAZIER), RecipeCategory.BUILDING_BLOCKS, Items.CACTUS, 50f, 500, "test");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BRAZIER, 1)
                .pattern(" B ")
                .pattern("B B")
                .pattern(" A ")
                .input('A', Items.CAMPFIRE)
                .input('B', Items.SOUL_LANTERN)
                .criterion(hasItem(Items.CAMPFIRE), conditionsFromItem(Items.CAMPFIRE))
                .offerTo(exporter, Identifier.of(getRecipeName(ModBlocks.BRAZIER)));
    }
}

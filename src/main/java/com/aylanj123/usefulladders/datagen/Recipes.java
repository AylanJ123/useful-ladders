package com.aylanj123.usefulladders.datagen;

import com.aylanj123.usefulladders.Registry;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registry.ROPED_LADDER.get(), 8)
            .pattern("LLL")
            .pattern("LSL")
            .pattern("LLL")
            .define('L', Items.LADDER)
            .define('S', Items.STRING)
            .group("roped_big")
            .unlockedBy(getHasName(Items.LADDER), has(Items.LADDER))
            .showNotification(true)
            .save(writer, "roped_big");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Registry.ROPED_LADDER.get(), 3)
            .requires(Items.LADDER, 3)
            .requires(Items.STRING, 1)
            .group("roped_small")
            .unlockedBy(getHasName(Items.LADDER), has(Items.LADDER))
            .save(writer, "roped_small");
    }

}

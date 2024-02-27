package com.aylanj123.usefulladders.datagen;

import com.aylanj123.usefulladders.UsefulLaddersMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {

    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UsefulLaddersMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(new ResourceLocation(UsefulLaddersMod.MODID, "roped_ladder"));
    }

}

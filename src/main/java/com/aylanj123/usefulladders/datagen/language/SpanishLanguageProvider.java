package com.aylanj123.usefulladders.datagen.language;

import com.aylanj123.usefulladders.LangKeys;
import com.aylanj123.usefulladders.UsefulLaddersMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class SpanishLanguageProvider extends LanguageProvider {

    public SpanishLanguageProvider(PackOutput output, String locale) {
        super(output, UsefulLaddersMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(LangKeys.ITEM_ROPE_LADDER.key(), "Escalera de cuerda");
    }
}

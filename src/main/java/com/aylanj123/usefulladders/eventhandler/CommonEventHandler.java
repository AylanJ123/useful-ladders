package com.aylanj123.usefulladders.eventhandler;
import com.aylanj123.usefulladders.Registry;
import com.aylanj123.usefulladders.UsefulLaddersMod;
import com.aylanj123.usefulladders.datagen.language.*;
import com.aylanj123.usefulladders.datagen.ItemModels;
import com.aylanj123.usefulladders.datagen.Recipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CommonEventHandler {

    @Mod.EventBusSubscriber(modid = UsefulLaddersMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModEvents {

        private static final List<String> englishLocales = new ArrayList<>(List.of(
                "en_us", "en_nz", "en_gb", "en_ca", "en_au"
        ));

        private static final List<String> spanishLocales = new ArrayList<>(List.of(
                "es_ve", "es_uy", "es_mx", "es_es", "es_ec", "es_cl", "es_ar"
        ));

        private static final List<String> germanLocales = new ArrayList<>(List.of(
                "de_de", "de_at", "de_ch", "nds_de"
        ));

        private static final List<String> portugueseLocales = new ArrayList<>(List.of(
                "pt_pt", "pt_br"
        ));

        @SubscribeEvent
        static void gatherData(GatherDataEvent event) {
            UsefulLaddersMod.LOGGER.info("Generating new data");
            DataGenerator gen = event.getGenerator();
            PackOutput output = gen.getPackOutput();
            ExistingFileHelper helper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookUp = event.getLookupProvider();

            for (String locale : englishLocales)
                gen.addProvider(
                    event.includeClient(),
                    new EnglishLanguageProvider(output, locale)
                );
            for (String locale : spanishLocales)
                gen.addProvider(
                    event.includeClient(),
                    new SpanishLanguageProvider(output, locale)
                );
            for (String locale : germanLocales)
                gen.addProvider(
                    event.includeClient(),
                    new GermanLanguageProvider(output, locale)
                );
            for (String locale : portugueseLocales)
                gen.addProvider(
                    event.includeClient(),
                    new PortugueseLanguageProvider(output, locale)
                );
            gen.addProvider(
                event.includeClient(),
                new SwedishLanguageProvider(output, "sv_se")
            );

            gen.addProvider(event.includeServer(), new Recipes(output));
            gen.addProvider(event.includeClient(), new ItemModels(output, helper));
        }

    }

}
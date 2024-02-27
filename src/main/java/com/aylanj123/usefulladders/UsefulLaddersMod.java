package com.aylanj123.usefulladders;

import com.aylanj123.usefulladders.eventhandler.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(UsefulLaddersMod.MODID)
public class UsefulLaddersMod
{
    public static final String MODID = "useful_ladders";

    // slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public UsefulLaddersMod()
    {
        MinecraftForge.EVENT_BUS.register(ServerEventHandler.ServerForgeEvents.class);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC, "useful_ladders.toml");
        Registry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}

package com.aylanj123.usefulladders.eventhandler;
import com.aylanj123.usefulladders.UsefulLaddersMod;
import com.aylanj123.usefulladders.Config;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler {

    @Mod.EventBusSubscriber(modid = UsefulLaddersMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        static void clientSetUp(FMLClientSetupEvent event) {
            UsefulLaddersMod.LOGGER.info("Setting up the client");
            Config.clientSidedLoad();
        }

    }

    @Mod.EventBusSubscriber(modid = UsefulLaddersMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientForgeEvents {

    }

}

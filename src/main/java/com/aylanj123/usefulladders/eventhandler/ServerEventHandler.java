package com.aylanj123.usefulladders.eventhandler;

import com.aylanj123.usefulladders.Config;
import com.aylanj123.usefulladders.UsefulLaddersMod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerEventHandler {

    public static class ServerForgeEvents {

        @SubscribeEvent
        static void serverSetUp(ServerStartingEvent event) {
            UsefulLaddersMod.LOGGER.info("Setting up the server");
            Config.serverSidedLoad();
        }

    }


}
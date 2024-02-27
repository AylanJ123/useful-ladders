package com.aylanj123.usefulladders.eventhandler;

import com.aylanj123.usefulladders.Config;
import com.aylanj123.usefulladders.UsefulLaddersMod;
import com.aylanj123.usefulladders.Helpers;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerEventHandler {

    public static class ServerForgeEvents {

        @SubscribeEvent
        static void serverSetUp(ServerStartingEvent event) {
            UsefulLaddersMod.LOGGER.info("Setting up the server");
            Config.serverSidedLoad();
        }

        @SubscribeEvent
        static void playerInteract(PlayerInteractEvent.RightClickBlock event) {
            if (event.getItemStack().getItem() != Items.LADDER) return;
            BlockState state = event.getLevel().getBlockState(event.getPos());
            if (state.getBlock() != Blocks.LADDER) return;
            if (!event.getLevel().isClientSide) {
                Helpers.RecursiveResult result = Helpers.placeLadder(event.getPos(), event.getLevel(), state, Direction.UP);
                if (result.success && !event.getEntity().isCreative()) event.getItemStack().setCount(event.getItemStack().getCount() - 1);
            }
        }

    }


}
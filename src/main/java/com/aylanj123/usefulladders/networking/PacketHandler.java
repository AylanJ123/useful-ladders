package com.aylanj123.usefulladders.networking;

import net.minecraft.server.level.ServerPlayer;

public class PacketHandler {

//    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
//            new ResourceLocation(UsefulLaddersMod.MODID, "main")
//        ).serverAcceptedVersions((status) -> true)
//        .clientAcceptedVersions((status) -> true)
//        .networkProtocolVersion(() -> "1")
//        .simpleChannel();

    public static void register() {
//        INSTANCE.messageBuilder(
//            MovedC2SPacket.class, 1, NetworkDirection.PLAY_TO_SERVER
//                ).encoder(MovedC2SPacket::encode)
//                .decoder(MovedC2SPacket::new)
//                .consumerMainThread(MovedC2SPacket::handle)
//                .add();
    }

    public static void sendServer(Object msg) {
//        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendPlayer(Object msg, ServerPlayer player) {
//        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

}

package com.aylanj123.usefulladders;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config
{
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
//    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_SPEC;
//    public static final ForgeConfigSpec CLIENT_SPEC;

    private static final ForgeConfigSpec.ConfigValue<Integer> MAX_STRESS;
    private static final ForgeConfigSpec.ConfigValue<Integer> MAX_HANG;

//    private static final ForgeConfigSpec.ConfigValue<Boolean> CHAT_CONFIRMATION;

    static {
        SERVER_BUILDER.push("Configs for Useful Ladders mod - Server Sided -");
//        CLIENT_BUILDER.push("Configs for AFK Command mod - Client Sided -");

        MAX_STRESS = SERVER_BUILDER
                .comment("Max amount of ladders that can be put upwards on top of a strongly placed one (On solid blocks). Set to -1 to make infinite or 0 to deactivate feature.")
                .define("maxStress", 3);
        MAX_HANG = SERVER_BUILDER
                .comment("Max amount of ladders that can be put upwards hanging from a strongly placed one (On sturdy blocks). Set to -1 to make infinite or 0 to deactivate feature.")
                .define("maxHang", -1);

//        CHAT_CONFIRMATION = CLIENT_BUILDER
//                .comment("Set to true if you want to receive a chat confirmation of your AFK state when self applied.")
//                .define("chatConfirmation", false);

        SERVER_BUILDER.pop();
//        CLIENT_BUILDER.pop();
        SERVER_SPEC = SERVER_BUILDER.build();
//        CLIENT_SPEC = CLIENT_BUILDER.build();

    }

    public static int maxStress;
    public static int maxHang;
//    public static boolean chatConfirmation;

    public static void serverSidedLoad()
    {
        maxStress = MAX_STRESS.get();
        maxHang = MAX_HANG.get();
    }

    public static void clientSidedLoad() {
//        chatConfirmation = CHAT_CONFIRMATION.get();
    }

}

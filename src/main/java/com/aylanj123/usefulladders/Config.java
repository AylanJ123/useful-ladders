package com.aylanj123.usefulladders;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config
{
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_SPEC;

    private static final ForgeConfigSpec.ConfigValue<Integer> MAX_STRESS;
    private static final ForgeConfigSpec.ConfigValue<Integer> MAX_HANG;
    private static final ForgeConfigSpec.ConfigValue<Float> RECOVERY_CHANCE;
    private static final ForgeConfigSpec.ConfigValue<Boolean> INVENTORY_RECOVERY;

    static {
        SERVER_BUILDER.push("Configs for Useful Ladders mod - Server Sided -");

        MAX_STRESS = SERVER_BUILDER
                .comment("Max amount of ladders that can be put upwards on top of a strongly placed one (On solid blocks). Set to -1 to make infinite or 0 to deactivate feature.")
                .define("maxStress", 3);
        MAX_HANG = SERVER_BUILDER
                .comment("Max amount of ladders that can be put upwards hanging from a strongly placed one (On sturdy blocks). Set to -1 to make infinite or 0 to deactivate feature.")
                .define("maxHang", -1);
        RECOVERY_CHANCE = SERVER_BUILDER
                .comment("Chances of being able of recovery the spent string when placing Roped Ladders. By default is 1/10 and it's slightly lossy. Else, it might become a string generator based on RNG.")
                .define("recoveryChance", 0.1f);
        INVENTORY_RECOVERY = SERVER_BUILDER
                .comment("Players will recover the string directly into their inventories instead of having it fall from the ladder.")
                .define("inventoryRecovery", false);

        SERVER_BUILDER.pop();
        SERVER_SPEC = SERVER_BUILDER.build();
    }

    public static int maxStress;
    public static int maxHang;
    public static float recoveryChance;
    public static boolean inventoryRecovery;

    public static void serverSidedLoad()
    {
        maxStress = MAX_STRESS.get();
        maxHang = MAX_HANG.get();
        recoveryChance = RECOVERY_CHANCE.get();
        inventoryRecovery = INVENTORY_RECOVERY.get();
    }

}

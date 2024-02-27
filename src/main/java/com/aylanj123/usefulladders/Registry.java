package com.aylanj123.usefulladders;

import com.aylanj123.usefulladders.items.RopeLadder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UsefulLaddersMod.MODID);
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<BlockItem> VANILLA_LADDER = VANILLA_ITEMS.register("ladder", () -> new BlockItem(Blocks.LADDER, new Item.Properties().rarity(Rarity.COMMON).stacksTo(128)));

    public static final RegistryObject<Item> ROPED_LADDER = ITEMS.register("rope_ladder", () -> new RopeLadder(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        VANILLA_ITEMS.register(bus);
    }

}

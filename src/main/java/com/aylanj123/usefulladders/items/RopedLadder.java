package com.aylanj123.usefulladders.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RopedLadder extends Item {
    public RopedLadder(Properties properties) {
        super(properties
                .rarity(Rarity.COMMON)
                .stacksTo(128)
        );
    }



}

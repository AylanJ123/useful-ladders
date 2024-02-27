package com.aylanj123.usefulladders;

public enum LangKeys {
    ITEM_ROPE_LADDER("items.rope_ladder");

    private final String key;

    LangKeys(String key) {
        this.key = UsefulLaddersMod.MODID + "." + key;
    }

    public String key() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
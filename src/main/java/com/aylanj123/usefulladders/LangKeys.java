package com.aylanj123.usefulladders;

public enum LangKeys {
    NAME("KEY");

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
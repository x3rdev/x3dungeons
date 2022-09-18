package com.github.x3rmination.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class X3DUNGEONSConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> swag_dragon_min_seperation;
    public static final ForgeConfigSpec.ConfigValue<Integer> swag_dragon_max_seperation;
    public static final ForgeConfigSpec.ConfigValue<Integer> piglin_camp_min_seperation;
    public static final ForgeConfigSpec.ConfigValue<Integer> piglin_camp_max_seperation;
    public static final ForgeConfigSpec.ConfigValue<Integer> piglin_forge_min_seperation;
    public static final ForgeConfigSpec.ConfigValue<Integer> piglin_forge_max_seperation;

    static {
        BUILDER.push("X3DUNGEONS Config");

        swag_dragon_min_seperation = BUILDER.comment("Minimum amount of chunks between each swag_dragon (dragon remains) structure").define("swag_dragon minimum separation", 50);
        swag_dragon_max_seperation = BUILDER.comment("Maximum amount of chunks between each swag_dragon (dragon remains) structure").define("swag_dragon maximum separation", 100);
        piglin_camp_min_seperation = BUILDER.comment("Minimum amount of chunks between each piglin_camp (dragon remains) structure").define("piglin_camp minimum separation", 30);
        piglin_camp_max_seperation = BUILDER.comment("Maximum amount of chunks between each piglin_camp (dragon remains) structure").define("piglin_camp maximum separation", 70);
        piglin_forge_min_seperation = BUILDER.comment("Minimum amount of chunks between each piglin_camp (dragon remains) structure").define("piglin_camp minimum separation", 30);
        piglin_forge_max_seperation = BUILDER.comment("Maximum amount of chunks between each piglin_camp (dragon remains) structure").define("piglin_camp maximum separation", 70);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

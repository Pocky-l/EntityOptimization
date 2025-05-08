package com.piglinmine.entityoptimization.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EntityOptimizationConfig {

    public static final General general;
    public static final ForgeConfigSpec COMMON_CONFIG;
    private static final ForgeConfigSpec.Builder COMMON_BUILDER;

    // Don't judge me! It's because of auto formatting moving the order around!
    static {
        COMMON_BUILDER = new ForgeConfigSpec.Builder();

        general = new General();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static class General {

        public final ForgeConfigSpec.ConfigValue<Integer> maxEntitiesOnServer;
        public final ForgeConfigSpec.ConfigValue<Boolean> enabledEntityKilling;

        General() {
            COMMON_BUILDER.push("general");

            COMMON_BUILDER.comment("Approximate number of mobs at which they will stop moving and reacting to anything. They will also start to disappear if the function is enabled");
            this.maxEntitiesOnServer = COMMON_BUILDER
                    .define("maxEntitiesOnServer",1666);

            COMMON_BUILDER.comment("If true - entities will disappear when the maximum number is reached");
            this.enabledEntityKilling = COMMON_BUILDER
                    .define("enabledEntityKilling",true);
            COMMON_BUILDER.pop();
        }
    }
}

package com.piglinmine.entityoptimization;

import com.piglinmine.entityoptimization.config.EntityOptimizationConfig;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class EntityOptimizationHelper {

    public static final int MOB_LOAD_THRESHOLD = EntityOptimizationConfig.general.maxEntitiesOnServer.get() * 30 * 20;
    public static final int MOB_LOAD_STEP = MOB_LOAD_THRESHOLD / 20;

    public static int tickCounter = 0;

    public static final Map<EntityType<?>, Integer> mobLoadLevels = new HashMap<>();

    public static void incrementEntityLoad(EntityType<?> type) {
        mobLoadLevels.put(type, mobLoadLevels.getOrDefault(type, 0) + 1);
    }

    public static void decreaseEntityLoad(EntityType<?> type, int amount) {
        mobLoadLevels.put(type, mobLoadLevels.getOrDefault(type, 0) - amount);
    }

    public static int getEntityLoadLevel(EntityType<?> type) {
        return mobLoadLevels.getOrDefault(type, 0);
    }
}

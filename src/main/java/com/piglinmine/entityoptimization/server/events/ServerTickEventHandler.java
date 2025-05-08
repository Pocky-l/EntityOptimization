package com.piglinmine.entityoptimization.server.events;

import com.piglinmine.entityoptimization.EntityOptimizationHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public class ServerTickEventHandler {

    @SubscribeEvent
    public void onPreServerTick(ServerTickEvent.Pre event) {
        EntityOptimizationHelper.tickCounter++;

        if (EntityOptimizationHelper.tickCounter > 30 * 20) {
            EntityOptimizationHelper.tickCounter = 0;

            EntityOptimizationHelper.mobLoadLevels.forEach((entityType, loadLevel) -> {
                if (loadLevel > 0) {
                    EntityOptimizationHelper.mobLoadLevels.put(entityType,
                            loadLevel - (EntityOptimizationHelper.MOB_LOAD_STEP / 2));
                }
            });
        }
    }

    public static void register(IEventBus eventBus) {
        eventBus.register(new ServerTickEventHandler());
    }
}

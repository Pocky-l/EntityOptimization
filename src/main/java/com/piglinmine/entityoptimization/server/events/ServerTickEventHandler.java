package com.piglinmine.entityoptimization.server.events;

import com.piglinmine.entityoptimization.EntityOptimizationHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerTickEventHandler {

    @SubscribeEvent
    public void onPreServerTick(TickEvent.ServerTickEvent event) {
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

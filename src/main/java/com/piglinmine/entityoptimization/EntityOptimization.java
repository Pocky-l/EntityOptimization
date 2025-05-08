package com.piglinmine.entityoptimization;

import com.piglinmine.entityoptimization.config.EntityOptimizationConfig;
import com.piglinmine.entityoptimization.server.events.ServerTickEventHandler;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EntityOptimization.MODID)
public class EntityOptimization {

    public static final String MODID = "entityoptimization";

    public static final Logger LOGGER = LogManager.getLogger("EntityOptimization");

    public EntityOptimization() {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON,
                EntityOptimizationConfig.COMMON_CONFIG, "EntityOptimization.toml");

        ServerTickEventHandler.register(NeoForge.EVENT_BUS);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info(String.format("The %s modification has been successfully loaded.", MODID.toUpperCase()));
    }
}

package com.piglinmine.entityoptimization;

import com.piglinmine.entityoptimization.config.EntityOptimizationConfig;
import com.piglinmine.entityoptimization.server.events.ServerTickEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EntityOptimization.MODID)
public class EntityOptimization {

    public static final String MODID = "entityoptimization";

    public static final Logger LOGGER = LogManager.getLogger("EntityOptimization");

    public EntityOptimization() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,
                EntityOptimizationConfig.COMMON_CONFIG, "EntityOptimization.toml");

        ServerTickEventHandler.register(MinecraftForge.EVENT_BUS);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info(String.format("The %s modification has been successfully loaded.", MODID.toUpperCase()));
    }
}

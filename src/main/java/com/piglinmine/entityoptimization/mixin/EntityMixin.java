package com.piglinmine.entityoptimization.mixin;

import com.piglinmine.entityoptimization.EntityOptimizationHelper;
import com.piglinmine.entityoptimization.config.EntityOptimizationConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    private EntityType<?> type;

    @Shadow
    public abstract void discard();

    @Shadow
    public int tickCount;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onEntityTick(CallbackInfo ci) {
        if (type == EntityType.PLAYER || type == EntityType.ITEM) return;

        EntityOptimizationHelper.incrementEntityLoad(type);

        if (EntityOptimizationConfig.general.enabledEntityKilling.get() &&
                EntityOptimizationHelper.getEntityLoadLevel(type) > EntityOptimizationHelper.MOB_LOAD_THRESHOLD) {
            EntityOptimizationHelper.decreaseEntityLoad(type, 200);
            discard();
        }

        double loadPercentage = ((double) EntityOptimizationHelper.getEntityLoadLevel(type) / EntityOptimizationHelper.MOB_LOAD_THRESHOLD) * 20;
        int currentTick = ((tickCount - 1) % 20) + 1;

        if (currentTick < loadPercentage || loadPercentage > 19) {
            ci.cancel();
        }
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void onEntityMove(CallbackInfo ci) {
        if (type == EntityType.PLAYER || type == EntityType.ITEM) return;

        double loadPercentage = ((double) EntityOptimizationHelper.getEntityLoadLevel(type) / EntityOptimizationHelper.MOB_LOAD_THRESHOLD) * 20;
        int currentTick = ((tickCount - 1) % 20) + 1;

        if (currentTick < loadPercentage || loadPercentage > 19) {
            ci.cancel();
        }
    }

}

package com.piglinmine.entityoptimization.mixin;

import com.piglinmine.entityoptimization.EntityOptimizationHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    private void onLivingEntityAiStep(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        EntityType<?> type = livingEntity.getType();

        if (type == EntityType.PLAYER || type == EntityType.ITEM || !EntityOptimizationHelper.mobLoadLevels.containsKey(type)) {
            return;
        }

        double loadPercentage = ((double) EntityOptimizationHelper.getEntityLoadLevel(type) / EntityOptimizationHelper.MOB_LOAD_THRESHOLD) * 20;
        int currentTick = ((livingEntity.tickCount - 1) % 20) + 1;

        if (currentTick < loadPercentage || loadPercentage > 19) {
            ci.cancel();
        }
    }
}

package com.piglinmine.entityoptimization.mixin;

import com.piglinmine.entityoptimization.EntityOptimizationHelper;
import com.piglinmine.entityoptimization.config.EntityOptimizationConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PrimedTnt.class)
public class TntMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onEntityTick(CallbackInfo ci) {

        PrimedTnt tnt = (PrimedTnt) (Object) this;

        EntityOptimizationHelper.incrementEntityLoad(EntityType.TNT);

        if (EntityOptimizationConfig.general.enabledEntityKilling.get() &&
                EntityOptimizationHelper.getEntityLoadLevel(EntityType.TNT) > EntityOptimizationHelper.MOB_LOAD_THRESHOLD) {
            EntityOptimizationHelper.decreaseEntityLoad(EntityType.TNT, 200);
            tnt.discard();
        }

        double loadPercentage = ((double) EntityOptimizationHelper.getEntityLoadLevel(EntityType.TNT) / EntityOptimizationHelper.MOB_LOAD_THRESHOLD) * 20;
        int currentTick = ((tnt.tickCount - 1) % 20) + 1;

        if (currentTick < loadPercentage || loadPercentage > 19) {
            ci.cancel();
        }
    }
}

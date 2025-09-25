package com.p1nero.tcrcore.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;

@Mixin(ExtraDamageInstance.class)
public class ExtraDamageInstanceMixin {

    @Shadow(remap = false)
    @Final
    private ExtraDamageInstance.ExtraDamage calculator;

    @Inject(method = "get", at = @At("RETURN"), cancellable = true, remap = false)
    private void tcr$get(LivingEntity attacker, ItemStack hurtItem, LivingEntity target, float baseDamage, CallbackInfoReturnable<Float> cir) {
        if(this.calculator == ExtraDamageInstance.EVISCERATE_LOST_HEALTH) {
            cir.setReturnValue(Math.min(cir.getReturnValue(), baseDamage * 3));
        }
    }
}

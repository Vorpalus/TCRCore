package com.p1nero.tcrcore.mixin;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageType;

@Mixin(value = GuardSkill.class)
public class GuardSkillMixin {

    @Inject(method = "isBlockableSource", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$isBlockableSource(DamageSource damageSource, boolean advanced, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!damageSource.is(EpicFightDamageType.PARTIAL_DAMAGE));
    }
}

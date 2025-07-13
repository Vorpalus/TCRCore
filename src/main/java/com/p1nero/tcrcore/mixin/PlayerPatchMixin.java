package com.p1nero.tcrcore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = PlayerPatch.class)
public class PlayerPatchMixin {
    @Inject(method = "getModifiedStaminaConsume", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$getModifiedStaminaConsume(float amount, CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(amount);
    }
}

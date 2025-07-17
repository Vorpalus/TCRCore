package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.capability.TCRPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.TakeDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEvent;

/**
 * 招架计数
 */
@Mixin(value = TakeDamageEvent.Attack.class, remap = false)
public abstract class TakeDamageEventAttackMixin extends PlayerEvent<ServerPlayerPatch> {

    @Shadow
    protected boolean parried;

    public TakeDamageEventAttackMixin(ServerPlayerPatch playerPatch, boolean cancelable) {
        super(playerPatch, cancelable);
    }

    @Inject(method = "setParried", at = @At("HEAD"))
    private void smc$setParry(boolean parried, CallbackInfo ci){
        if(!this.parried && parried) {
            TCRPlayer.addSkillPoint(this.getPlayerPatch().getOriginal());
        }
    }
}

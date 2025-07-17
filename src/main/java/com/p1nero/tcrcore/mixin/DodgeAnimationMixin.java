package com.p1nero.tcrcore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;

import static yesman.epicfight.api.animation.types.DodgeAnimation.IGNORE_ALL_PROJECTILES;

@Mixin(value = DodgeAnimation.class)
public abstract class DodgeAnimationMixin  extends ActionAnimation {

    public DodgeAnimationMixin(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
    }

    @Inject(method = "<init>(FFLyesman/epicfight/api/animation/AnimationManager$AnimationAccessor;FFLyesman/epicfight/api/asset/AssetAccessor;)V", at = @At("TAIL"), remap = false)
    private void tcr$init(float transitionTime, float delayTime, AnimationManager.AnimationAccessor<?> accessor, float width, float height, AssetAccessor<?> armature, CallbackInfo ci){
        this.stateSpectrumBlueprint.clear()
                .newTimePair(0.0F, delayTime)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.INACTION, true)
                .newTimePair(0.0F, Float.MAX_VALUE)
                .addState(EntityState.ATTACK_RESULT, (damagesource) ->
                        !damagesource.is(EpicFightDamageTypeTags.BYPASS_DODGE) ? AttackResult.ResultType.MISSED : AttackResult.ResultType.SUCCESS);
    }

}

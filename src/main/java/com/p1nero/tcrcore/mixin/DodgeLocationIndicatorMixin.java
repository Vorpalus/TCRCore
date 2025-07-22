package com.p1nero.tcrcore.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.entity.DodgeLocationIndicator;

/**
 * 完美闪避的一些全局设定
 */
@Mixin(value = DodgeLocationIndicator.class)
public abstract class DodgeLocationIndicatorMixin extends LivingEntity {

    @Shadow(remap = false) private LivingEntityPatch<?> entitypatch;

    protected DodgeLocationIndicatorMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "hurt", at = @At("HEAD"), remap = false, cancellable = true)
    private void tcr$hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir){
        if(!this.entitypatch.isLogicalClient()) {
           if (!damageSource.is(EpicFightDamageTypeTags.BYPASS_DODGE)) {
                this.entitypatch.onDodgeSuccess(damageSource, this.position());
            }
            this.discard();
            cir.setReturnValue(false);
        }
    }
}

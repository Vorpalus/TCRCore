package com.p1nero.tcrcore.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.LiechtenauerSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

@Mixin(LiechtenauerSkill.class)
public abstract class LiechtenauerSkillMixin extends WeaponInnateSkill {

    @Shadow(remap = false)
    @Final
    private static UUID EVENT_UUID;

    @Shadow(remap = false)
    private static boolean isBlockableSource(DamageSource damageSource) {
        return false;
    }

    @Shadow(remap = false)
    private int returnDuration;

    public LiechtenauerSkillMixin(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Inject(method = "onInitiate", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$onInitiate(SkillContainer container, CallbackInfo ci) {
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.DEAL_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
            if (this.isActivated(container) && !this.isDisabled(container)) {
                if (event.getAttackDamage() > event.getTarget().getHealth()) {
                    this.setDurationSynchronize(container, Math.min(this.maxDuration, container.getRemainDuration() + this.returnDuration));
                }
            }
        });

        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.TAKE_DAMAGE_EVENT_ATTACK, EVENT_UUID, (event) -> {
            int phaseLevel = event.getPlayerPatch().getEntityState().getLevel();

            if (event.getDamage() > 0.0F && this.isActivated(container) && !this.isDisabled(container) && phaseLevel > 0 && phaseLevel < 3 &&
                    this.canExecute(container) && isBlockableSource(event.getDamageSource())) {
                DamageSource damageSource = event.getDamageSource();
                boolean isFront = false;
                Vec3 sourceLocation = damageSource.getSourcePosition();

                if (sourceLocation != null) {
                    Vec3 viewVector = event.getPlayerPatch().getOriginal().getViewVector(1.0F);
                    Vec3 toSourceLocation = sourceLocation.subtract(event.getPlayerPatch().getOriginal().position()).normalize();

                    if (toSourceLocation.dot(viewVector) > 0.0D) {
                        isFront = true;
                    }
                }

                if (isFront) {
                    event.getPlayerPatch().playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
                    ServerPlayer playerentity = event.getPlayerPatch().getOriginal();
                    EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(playerentity.serverLevel(), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());

                    float knockback = 0.25F;

                    if (damageSource instanceof EpicFightDamageSource epicfightSource) {
                        knockback += Math.min(epicfightSource.calculateImpact() * 0.1F, 1.0F);
                    }

                    if (damageSource.getDirectEntity() instanceof LivingEntity livingentity) {
                        knockback += EnchantmentHelper.getKnockbackBonus(livingentity) * 0.1F;
                    }

                    EpicFightCapabilities.getUnparameterizedEntityPatch(event.getDamageSource().getEntity(), LivingEntityPatch.class).ifPresent(attackerpatch -> {
                        attackerpatch.setLastAttackEntity(event.getPlayerPatch().getOriginal());
                    });

                    event.getPlayerPatch().knockBackEntity(damageSource.getDirectEntity().position(), knockback);
                    event.setCanceled(true);
                    event.setResult(AttackResult.ResultType.BLOCKED);
                    event.setParried(true);
                }
            }
        }, 0);

        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, (event) -> {
            if (this.isActivated(container)) {
                LocalPlayer clientPlayer = event.getPlayerPatch().getOriginal();
                clientPlayer.setSprinting(false);
                clientPlayer.sprintTriggerTime = -1;
                ControlEngine.setKeyBind(Minecraft.getInstance().options.keySprint, false);
            }
        });
        ci.cancel();
    }
}

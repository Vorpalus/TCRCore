package com.p1nero.tcrcore.capability.item;

import java.util.List;
import javax.annotation.Nullable;

import com.p1nero.tcrcore.gameassets.TCRAnimations;
import com.p1nero.tcrcore.gameassets.TCRSkills;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;
import yesman.epicfight.world.capabilities.item.Style;

public class WrathOfTheDesertCapability extends RangedWeaponCapability {
    private List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> attackMotion;
    private List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> mountAttackMotion;

    public WrathOfTheDesertCapability(CapabilityItem.Builder builder) {
        super(builder);
        this.attackMotion = List.of(TCRAnimations.BOW_1, TCRAnimations.BOW_2, TCRAnimations.BOW_3, TCRAnimations.BOW_2, TCRAnimations.BOW_2);
        this.mountAttackMotion = List.of(Animations.SWORD_MOUNT_ATTACK);
    }

    public Style getStyle(LivingEntityPatch<?> entitypatch) {
        return Styles.ONE_HAND;
    }

    public SoundEvent getHitSound() {
        return EpicFightSounds.BLADE_HIT.get();
    }

    public HitParticleType getHitParticle() {
        return EpicFightParticles.HIT_BLADE.get();
    }

    public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getAutoAttackMotion(PlayerPatch<?> playerpatch) {
        return this.attackMotion;
    }

    public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getMountAttackMotion() {
        return this.mountAttackMotion;
    }

    @Override
    public LivingMotion getLivingMotion(LivingEntityPatch<?> entityPatch, InteractionHand hand) {
        return entityPatch.getOriginal().isUsingItem() && entityPatch.getOriginal().getUseItem().getUseAnimation() == UseAnim.BOW ? LivingMotions.AIM : null;
    }

    @Nullable
    public Skill getInnateSkill(PlayerPatch<?> playerpatch, ItemStack itemstack) {
        return TCRSkills.WRATH_OF_THE_DESERT_INNATE;
    }
}

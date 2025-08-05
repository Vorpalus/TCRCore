package com.p1nero.tcrcore.gameassets;

import com.github.L_Ender.cataclysm.capabilities.RenderRushCapability;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.client.particle.RoarParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Lightning_Storm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.*;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.items.Ceraunus;
import com.hm.efn.registries.NightFallEffectsRegistry;
import com.hm.efn.util.EffectEntityInvoker;
import com.hm.efn.util.WeaponTrailGroundSplitter;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.particle.AvalonParticles;
import com.merlin204.avalon.util.AvalonEventUtils;
import com.p1nero.invincible.animations.ScanAttackAnimation;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

import static com.hm.efn.animations.EFNAnimations.MEEN_LANCE_1;
import static com.hm.efn.animations.EFNAnimations.MEEN_LANCE_CHARGE3;
import static com.merlin204.avalon.util.AvalonAnimationUtils.createSimplePhase;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRAnimations {
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_B;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_F;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_L;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_R;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TSUNAMI;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(TCRCoreMod.MOD_ID, (builder) -> {
            STEP_F = builder.nextAccessor("dodge/step_forward", (accessor) ->
                    new DodgeAnimation(0.1F, 0.35F, accessor, 0.6F, 1.65F, Armatures.BIPED)
                            .addState(EntityState.LOCKON_ROTATE, true)
                            .newTimePair(0.0F, 0.2F)
                            .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                            .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                            .addEvents(AnimationEvent.InTimeEvent.create(0.0F,
                                    Animations.ReusableSources.PLAY_STEPPING_SOUND, AnimationEvent.Side.CLIENT)));
            STEP_B = builder.nextAccessor("dodge/step_backward", (accessor) ->
                    new DodgeAnimation(0.1F, 0.35F, accessor, 0.6F, 1.65F, Armatures.BIPED)
                            .addState(EntityState.LOCKON_ROTATE, true)
                            .newTimePair(0.0F, 0.2F)
                            .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                            .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                            .addEvents(AnimationEvent.InTimeEvent.create(0.0F,
                                    Animations.ReusableSources.PLAY_STEPPING_SOUND, AnimationEvent.Side.CLIENT)));
            STEP_L = builder.nextAccessor("dodge/step_left", (accessor) ->
                    new DodgeAnimation(0.1F, 0.35F, accessor, 0.6F, 1.65F, Armatures.BIPED)
                            .addState(EntityState.LOCKON_ROTATE, true)
                            .newTimePair(0.0F, 0.2F)
                            .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                            .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                            .addEvents(AnimationEvent.InTimeEvent.create(0.0F,
                                    Animations.ReusableSources.PLAY_STEPPING_SOUND, AnimationEvent.Side.CLIENT)));
            STEP_R = builder.nextAccessor("dodge/step_right", (accessor) ->
                    new DodgeAnimation(0.1F, 0.35F, accessor, 0.6F, 1.65F, Armatures.BIPED)
                            .addState(EntityState.LOCKON_ROTATE, true)
                            .newTimePair(0.0F, 0.2F)
                            .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                            .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                            .addEvents(AnimationEvent.InTimeEvent.create(0.0F,
                                    Animations.ReusableSources.PLAY_STEPPING_SOUND, AnimationEvent.Side.CLIENT)));
            TSUNAMI = builder.nextAccessor("skill/tsunami_reinforced", (accessor) ->
                    (new AttackAnimation(0.1F, 0.2F, 0.35F, 0.65F, 0.8F, ColliderPreset.BIPED_BODY_COLLIDER, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED))
                            .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(10.0F))
                            .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.RAW_COORD_WITH_X_ROT)
                            .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, null)
                            .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                            .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.15F, 0.85F))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                            .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.ROOT_X_MODIFIER)
                            .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create(Animations.ReusableSources.RESTORE_BOUNDING_BOX, AnimationEvent.Side.BOTH))
                            .addEvents(AnimationProperty.StaticAnimationProperty.TICK_EVENTS, AnimationEvent.SimpleEvent.create(Animations.ReusableSources.RESIZE_BOUNDING_BOX, AnimationEvent.Side.BOTH)
                                    .params(EntityDimensions.scalable(0.6F, 1.0F)))
                            .newTimePair(0.0F, 0.8F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (source -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InPeriodEvent.create(0.35F, 1.0F, (entitypatch, animation, params) -> {
                                Vec3 pos = entitypatch.getOriginal().position();
                                for (int x = -1; x <= 1; x += 2) {
                                    for (int z = -1; z <= 1; z += 2) {
                                        Vec3 rand = (new Vec3(Math.random() * (double) x, Math.random(), Math.random() * (double) z)).normalize().scale((double) 2.0F);
                                        entitypatch.getOriginal().level().addParticle(EpicFightParticles.TSUNAMI_SPLASH.get(), pos.x + rand.x, pos.y + rand.y - (double) 1.0F, pos.z + rand.z, rand.x * 0.1, rand.y * 0.1, rand.z * 0.1);
                                    }
                                }
                            }, AnimationEvent.Side.CLIENT))
                            .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.35F, (entitypatch, animation, params) ->
                                    entitypatch.playSound(SoundEvents.TRIDENT_RIPTIDE_3, 0.0F, 0.0F), AnimationEvent.Side.CLIENT),
                                    AnimationEvent.InTimeEvent.create(0.35F, (entitypatch, animation, params) ->
                                            entitypatch.setAirborneState(true), AnimationEvent.Side.SERVER)}));

        });
    }
}

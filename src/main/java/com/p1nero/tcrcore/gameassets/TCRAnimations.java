package com.p1nero.tcrcore.gameassets;

import com.github.L_Ender.cataclysm.capabilities.RenderRushCapability;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.client.particle.RoarParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
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
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.animations.ScanAttackAnimation;
import com.p1nero.tcrcore.utils.TCREffectConditionParticleTrail;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
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
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_1;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_2;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_3;

    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_SKILL1;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_SKILL2;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_SKILL3;
    public static AnimationManager.AnimationAccessor<ActionAnimation> CERAUNUS_SKILL1;
    public static AnimationManager.AnimationAccessor<ActionAnimation> CERAUNUS_SKILL2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> CERAUNUS_SKILL3;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> SOUL_RENDER_SKILL1;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> SOUL_RENDER_SKILL2;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> SOUL_RENDER_SKILL3;
    public static AnimationManager.AnimationAccessor<ActionAnimation> THE_INCINERATOR_SKILL1;
    public static AnimationManager.AnimationAccessor<ActionAnimation> THE_INCINERATOR_SKILL2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> THE_INCINERATOR_SKILL3;

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
            BOW_1 = builder.nextAccessor("bow/bow_auto1", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 0.5F, 0.5F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .addEvents(AnimationEvent.InTimeEvent.create(0.49F,
                                    shootPhantomArrow(1.0F), AnimationEvent.Side.SERVER))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));
            BOW_2 = builder.nextAccessor("bow/bow_auto2", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 1.1F, 1.2F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .addEvents(AnimationEvent.InTimeEvent.create(1.1F,
                                    shootPhantomArrow(1.5F), AnimationEvent.Side.SERVER))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));
            BOW_3 = builder.nextAccessor("bow/bow_auto3", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 1.5F, 1.5F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .addEvents(AnimationEvent.InTimeEvent.create(0.7F,
                                            shootPhantomArrow(0.7F, false), AnimationEvent.Side.SERVER),
                                    AnimationEvent.InTimeEvent.create(0.85F,
                                            shootPhantomArrow(0.7F, false), AnimationEvent.Side.SERVER),
                                    AnimationEvent.InTimeEvent.create(1.0F,
                                            shootPhantomArrow(0.7F), AnimationEvent.Side.SERVER))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));

            BOW_SKILL1 = builder.nextAccessor("skill/bow_skill1", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 0.5F, 0.5F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .newTimePair(0.0F, 1.1F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InTimeEvent.create(0.49F,
                                    shootSandstorm(0.8F, true), AnimationEvent.Side.SERVER))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));

            BOW_SKILL2 = builder.nextAccessor("skill/bow_skill2", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 1.1F, 1.2F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .newTimePair(0.0F, 1.1F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InTimeEvent.create(1.1F,
                                    shootCursedSandstorm(1.1F, true), AnimationEvent.Side.SERVER))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));

            BOW_SKILL3 = builder.nextAccessor("skill/bow_skill3", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 4.0F, 5.0F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .newTimePair(0.0F, 3.0F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InTimeEvent.create(1.0F,
                                            shootSandstormByView(1.0F, false), AnimationEvent.Side.SERVER),
                                    AnimationEvent.InTimeEvent.create(1.6F,
                                            shootCursedSandstorm(1.0F, false), AnimationEvent.Side.SERVER),
                                    AnimationEvent.InTimeEvent.create(2.2F,
                                            shootThreeStorm(false), AnimationEvent.Side.SERVER),
                                    AvalonEventUtils.simpleCameraShake((int) (2.2 * 60), 60, 7,6, 6),
                                    AnimationEvent.InTimeEvent.create(3.0F,
                                            shootRain(true), AnimationEvent.Side.BOTH)
                            )
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));

            CERAUNUS_SKILL1 = builder.nextAccessor("skill/ceraunus_skill1", (accessor) ->
                    new ActionAnimation(0.15F, 1.5F, accessor, Armatures.BIPED)
                            .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                            .newTimePair(0.0F, 1.5F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InTimeEvent.create(0.5F, shootCeraunus(), AnimationEvent.Side.SERVER)));

            SOUL_RENDER_SKILL1 = builder.nextAccessor("skill/soul_render_skill1", accessor -> new AvalonAttackAnimation(0.15F, accessor, Armatures.BIPED, 1.1F, 1,
                    createSimplePhase(38, 56, 75, InteractionHand.MAIN_HAND, 1.5F, 1.5F, Armatures.BIPED.get().toolR, null))
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                    .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(80))
                    .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                    .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE, EpicFightDamageTypeTags.GUARD_PUNCTURE))
                    .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                    .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                    .newTimePair(0.0F, 1.3F)
                    .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                    .newTimePair(0.0F, Float.MAX_VALUE)
                    .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1F))
                    .addEvents(
                            EffectEntityInvoker.clearFireWind(75),
                            AnimationEvent.InTimeEvent.create(0.1F, (entityPatch, self, params) -> {
                                entityPatch.getOriginal().addEffect(new MobEffectInstance(NightFallEffectsRegistry.SIN_STUN_IMMUNITY.get(), 20, 10, false, false, false));
                            }, AnimationEvent.Side.SERVER),
                            AnimationEvent.InTimeEvent.create(0.1F, (entityPatch, animation, params) -> {
                                LivingEntity entity = entityPatch.getOriginal();
                                entity.level().addParticle(AvalonParticles.AVALON_ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                            }, AnimationEvent.Side.CLIENT),
                            AvalonEventUtils.simpleCameraShake(43, 40, 3, 3, 3),
                            TCREffectConditionParticleTrail.buffedParticleTrail(0, 55, InteractionHand.MAIN_HAND, new Vec3(0, 0, -1.29F), new Vec3(0, 0, -1.3F), 8, 2, ModParticle.PHANTOM_WING_FLAME.get(), 0F),
                            AnimationEvent.InTimeEvent.create(0.6F, (entityPatch, self, params) -> {
                                RenderRushCapability.IRenderRushCapability ChargeCapability = ModCapabilities.getCapability(entityPatch.getOriginal(), ModCapabilities.RENDER_RUSH_CAPABILITY);
                                if (ChargeCapability != null) {
                                    ChargeCapability.setRush(true);
                                    ChargeCapability.setTimer(30 / 2);
                                    ChargeCapability.setdamage(0.1F);
                                }
                            }, AnimationEvent.Side.BOTH)
                    )
            );

            SOUL_RENDER_SKILL2 = builder.nextAccessor("skill/soul_render_skill2", accessor -> new AvalonAttackAnimation(0.15F, accessor, Armatures.BIPED, 1.2F, 1,
                    createSimplePhase(52, 75, 90, InteractionHand.MAIN_HAND, 2.0F, 8.0F, Armatures.BIPED.get().toolR, MEEN_LANCE_1))
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(120))
                    .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                    .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                    .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE, EpicFightDamageTypeTags.GUARD_PUNCTURE, EpicFightDamageTypeTags.BYPASS_DODGE))
                    .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                    .newTimePair(0.0F, 1.0F)
                    .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                    .newTimePair(0.0F, Float.MAX_VALUE)
                    .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1F))
                    .addEvents(EffectEntityInvoker.clearFireWind(80),
                            AnimationEvent.InTimeEvent.create(0.1F, (entityPatch, animation, params) -> {
                                LivingEntity entity = entityPatch.getOriginal();
                                entity.level().addParticle(AvalonParticles.AVALON_ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
                            }, AnimationEvent.Side.CLIENT),
                            AnimationEvent.InTimeEvent.create(0.1F, (entityPatch, self, params) -> {
                                entityPatch.getOriginal().addEffect(new MobEffectInstance(NightFallEffectsRegistry.SIN_STUN_IMMUNITY.get(), 20, 10, false, false, false));
                            }, AnimationEvent.Side.SERVER),
                            AvalonEventUtils.simpleCameraShake(53, 70, 7, 6, 6),
                            TCREffectConditionParticleTrail.buffedParticleTrail(56, 70, InteractionHand.MAIN_HAND
                                    , new Vec3(0, 0, -2.25F), new Vec3(0, 0, -2.3F), 12, 3, ModParticle.SOUL_LAVA.get(), 0.3F),
                            TCREffectConditionParticleTrail.buffedParticleTrail(52, 70, InteractionHand.MAIN_HAND
                                    , new Vec3(-0.2F, 0, -2.25F), new Vec3(-0.2F, 0, -2.3F), 8, 10, ModParticle.PHANTOM_WING_FLAME.get(), 0.3F),
                            WeaponTrailGroundSplitter.create(54, 80, InteractionHand.MAIN_HAND
                                    , new Vec3(0, 0, -2.25F), new Vec3(0, 0, -2.3F),
                                    1.8F, ModParticle.PHANTOM_WING_FLAME.get(), 0, 3F, 6, 15),
                            AnimationEvent.InTimeEvent.create(1.0F, (entityPatch, self, params) -> {
                                double radius = 6.0F;
                                Level world = entityPatch.getOriginal().level();
                                LivingEntity caster = entityPatch.getOriginal();
                                ScreenShake_Entity.ScreenShake(world, caster.position(), 30.0F, 0.1F, 0, 30);
                                world.playSound(null, caster.getX(), caster.getY(), caster.getZ(), ModSounds.MALEDICTUS_SHORT_ROAR.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
                                world.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 1.0F / (caster.getRandom().nextFloat() * 0.4F + 0.8F));

                                for(Entity entity : world.getEntities(caster, caster.getBoundingBox().inflate(radius, radius, radius))) {
                                    if (entity instanceof LivingEntity living && living.isAlive()) {
                                        living.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), 60, 0));
                                    }
                                }
                                if (world.isClientSide) {
                                    world.addParticle(new RingParticle.RingData(0.0F, ((float)Math.PI / 2F), 30, 0.337F, 0.925F, 0.8F, 1.0F, 85.0F, false, RingParticle.EnumRingBehavior.GROW), caster.getX(), caster.getY() + (double)0.03F, caster.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
                                }
                            }, AnimationEvent.Side.BOTH)
                    )
            );

            SOUL_RENDER_SKILL3 = builder.nextAccessor("skill/soul_render_skill3", accessor -> new AvalonAttackAnimation(0.15F, accessor, Armatures.BIPED, 1, 1,
                            createSimplePhase(59, 100, 120, InteractionHand.MAIN_HAND, 2.0F, 0, Armatures.BIPED.get().rootJoint, MEEN_LANCE_CHARGE3))
                            .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                            .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                            .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                            .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100))
                            .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                            .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                            .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                            .newTimePair(0.0F, 1.5F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .newTimePair(0.0F, Float.MAX_VALUE)
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1F))
                            .addEvents(// 第一阶段：火焰爆发效果（客户端）
                                    AnimationEvent.InTimeEvent.create(1.1F, (entityPatch, self, params) -> {
                                        if (entityPatch.getOriginal().level().isClientSide()) {
                                            LivingEntity attacker = entityPatch.getOriginal();
                                            Level level = attacker.level();

                                            // ===== 强化参数配置 =====
                                            float intensity = 2.5f; // 增强强度系数
                                            int coneParticles = (int) (150 * intensity); // 增加锥形粒子数

                                            // ===== 改进版：完整形状的扩散火圈 =====
                                            Vec3 centerPos = attacker.position().add(0, 0.05, 0); // 中心位置
                                            int totalRings = 5; // 总圈数
                                            int particlesPerRing = 36; // 每圈粒子数(36个粒子形成完整圆)
                                            float maxRadius = 5.0f; // 最大半径
                                            float duration = 0.5f; // 扩散持续时间(秒)
                                            float currentTime = 0.0f; // 当前时间(用于动画)

                                            currentTime += 0.05f; // 假设每帧0.05秒
                                            float progress = Math.min(currentTime / duration, 1.0f);

                                            for (int ring = 0; ring < totalRings; ring++) {
                                                // 当前圈的半径(从内到外)
                                                float radius = maxRadius * (ring + progress) / totalRings;

                                                // 完整圆形生成
                                                for (int i = 0; i < particlesPerRing; i++) {
                                                    float angle = (float) (6.283185307179586 * i / particlesPerRing);
                                                    float xOffset = radius * Mth.cos(angle);
                                                    float zOffset = radius * Mth.sin(angle);

                                                    // 基础火焰粒子
                                                    level.addParticle(
                                                            ModParticle.PHANTOM_WING_FLAME.get(),
                                                            centerPos.x + xOffset,
                                                            centerPos.y + 0.05f, // 紧贴地面
                                                            centerPos.z + zOffset,
                                                            xOffset * 0.1f, // 轻微的向外速度
                                                            0.08f, // 轻微上浮
                                                            zOffset * 0.1f
                                                    );

                                                    // 每5个粒子添加一个强化粒子
                                                    if (i % 5 == 0) {
                                                        level.addParticle(
                                                                ModParticle.SOUL_LAVA.get(), // 使用灵魂火增加视觉效果
                                                                centerPos.x + xOffset,
                                                                centerPos.y + 0.07f,
                                                                centerPos.z + zOffset,
                                                                xOffset * 0.15f,
                                                                0.12f,
                                                                zOffset * 0.15f
                                                        );
                                                    }
                                                }
                                            }


                                            // ===== 第一阶段：武器球状粒子 =====
                                            OpenMatrix4f transformMatrix = entityPatch.getArmature()
                                                    .getBoundTransformFor(entityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().toolR);

                                            transformMatrix.translate(new Vec3f(-0.2F, 0.0F, 0.4F));
                                            OpenMatrix4f.mul(
                                                    new OpenMatrix4f().rotate(-(float) Math.toRadians(attacker.yBodyRotO + 180.0F),
                                                            new Vec3f(0.0F, 1.0F, 0.0F)),
                                                    transformMatrix,
                                                    transformMatrix
                                            );
                                            // 70个球状粒子（完全复刻原版）
                                            for (int i = 0; i < 70; ++i) {
                                                double theta = 6.283185307179586 * Math.random();
                                                double thetax = Math.acos(2.0 * Math.random() - 1.0);
                                                float dx = (float) (0.1 * Math.sin(thetax) * Math.cos(theta));
                                                float dy = (float) (0.1 * Math.sin(thetax) * Math.sin(theta));
                                                float dz = (float) (0.1 * Math.cos(thetax));

                                                level.addParticle(
                                                        ModParticle.PHANTOM_WING_FLAME.get(),
                                                        transformMatrix.m30 + attacker.getX(),
                                                        transformMatrix.m31 + attacker.getY() + (float) (Math.random() * 2.9F),
                                                        transformMatrix.m32 + attacker.getZ(),
                                                        dx, dy, dz
                                                );

                                                if (i % 2 == 0) {
                                                    level.addParticle(
                                                            ModParticle.SOUL_LAVA.get(),
                                                            transformMatrix.m30 + attacker.getX(),
                                                            transformMatrix.m31 + attacker.getY() + (float) (Math.random() * 2.9F),
                                                            transformMatrix.m32 + attacker.getZ(),
                                                            dx, dy, dz
                                                    );
                                                }
                                            }


                                            // ===== 双锥形冲击波 =====
                                            transformMatrix = entityPatch.getArmature()
                                                    .getBoundTransformFor(entityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().chest);
                                            OpenMatrix4f.mul(
                                                    new OpenMatrix4f().rotate(-(float) Math.toRadians(attacker.yBodyRotO + 180.0F),
                                                            new Vec3f(0.0F, 1.0F, 0.0F)),
                                                    transformMatrix,
                                                    transformMatrix
                                            );
                                            transformMatrix.translate(new Vec3f(0.0F, 0.3F, -0.5F)); // 调整发射位置

                                            double r = 0.5 * intensity; // 扩大半径
                                            double t = 0.006; // 调整锥形角度

                                            // 强化双锥形（每组粒子数增加）
                                            for (int group = 0; group < 2; group++) {
                                                float angle = group == 0 ? 110.0f : 70.0f;

                                                for (int i = 0; i < coneParticles; ++i) {
                                                    double theta = 6.283185307179586 * Math.random();
                                                    double phi = (Math.random() - 0.2) * Math.PI * t / r; // 调整分布

                                                    Vec3f direction = new Vec3f(
                                                            (float) (r * Math.cos(phi) * Math.cos(theta)),
                                                            (float) (r * Math.cos(phi) * Math.sin(theta)) * 1.5f, // 增强垂直效果
                                                            (float) (r * Math.sin(phi))
                                                    );

                                                    OpenMatrix4f rotation = new OpenMatrix4f()
                                                            .rotate((float) Math.toRadians(-attacker.yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F))
                                                            .rotate((float) Math.toRadians(angle), new Vec3f(1.0F, 0.0F, 0.0F));
                                                    OpenMatrix4f.transform3v(rotation, direction, direction);

                                                    float speedVariation = 0.3f + 0.3f * (float) Math.random();
                                                    direction.scale(speedVariation);

                                                    // 添加粒子多样性
                                                    ParticleOptions particle;
                                                    if (Math.random() < 0.7) {
                                                        particle = ModParticle.PHANTOM_WING_FLAME.get();
                                                    } else {
                                                        particle = ParticleTypes.END_ROD; // 添加火星效果
                                                    }

                                                    level.addParticle(
                                                            particle,
                                                            transformMatrix.m30 + attacker.getX(),
                                                            transformMatrix.m31 + attacker.getY() + 0.7f,
                                                            transformMatrix.m32 + attacker.getZ(),
                                                            direction.x * 1.3f,
                                                            direction.y * 1.5f, // 增强上升效果
                                                            direction.z * 1.3f
                                                    );

                                                    // 随机熔岩尾迹
                                                    if (Math.random() < 0.2f) {
                                                        Vec3f lavaDir = new Vec3f(direction.x, direction.y, direction.z).scale(0.7f);
                                                        level.addParticle(
                                                                ModParticle.SOUL_LAVA.get(),
                                                                transformMatrix.m30 + attacker.getX(),
                                                                transformMatrix.m31 + attacker.getY(),
                                                                transformMatrix.m32 + attacker.getZ(),
                                                                lavaDir.x, lavaDir.y * 1.4f, lavaDir.z
                                                        );
                                                    }
                                                }
                                            }

                                            // ===== 地面燃烧效果 =====
                                            for (int i = 0; i < 120 * intensity; i++) {
                                                double angle = Math.random() * 6.283185307179586;
                                                double radius = 4.0 * intensity * Math.random();
                                                Vec3 pos = new Vec3(
                                                        radius * Math.cos(angle),
                                                        0.1,
                                                        radius * Math.sin(angle)
                                                ).add(attacker.position());

                                                // 主火焰粒子
                                                level.addParticle(
                                                        Math.random() < 0.6 ? ParticleTypes.SOUL_FIRE_FLAME : ModParticle.PHANTOM_WING_FLAME.get(),
                                                        pos.x, pos.y, pos.z,
                                                        (Math.random() - 0.5) * 0.25,
                                                        0.15 + Math.random() * 0.4,
                                                        (Math.random() - 0.5) * 0.25
                                                );

                                                // 熔岩斑块
                                                if (i % 4 == 0) {
                                                    level.addParticle(
                                                            ModParticle.SOUL_LAVA.get(),
                                                            pos.x, pos.y + 0.05, pos.z,
                                                            0, 0.03, 0
                                                    );
                                                }

                                                // 烟雾效果
                                                if (i % 3 == 0) {
                                                    level.addParticle(
                                                            ParticleTypes.ENCHANT,
                                                            pos.x, pos.y + 0.1, pos.z,
                                                            (Math.random() - 0.5) * 0.1,
                                                            0.1,
                                                            (Math.random() - 0.5) * 0.1
                                                    );
                                                }
                                            }
                                        }
                                    }, AnimationEvent.Side.CLIENT),
                                    // 第二阶段：冲击波效果（服务端）
                                    AnimationEvent.InTimeEvent.create(1.85F, (entityPatch, self, params) -> {
                                        if (!entityPatch.getOriginal().level().isClientSide()) {
                                            LivingEntity attacker = entityPatch.getOriginal();
                                            ServerLevel level = (ServerLevel) attacker.level();

                                            // 强化冲击波参数
                                            double centerX = attacker.getX();
                                            double centerY = attacker.getY() + 0.2; // 稍微抬升中心点
                                            double centerZ = attacker.getZ();
                                            double baseRadius = 10.0; // 扩大基础半径
                                            double maxRadius = 18.0; // 扩大最大半径
                                            int waveCount = 4; // 增加波次
                                            int particlesPerWave = 100; // 增加每波粒子数
                                            double speed = 0.5; // 提高速度

                                            // 多层冲击波
                                            for (int wave = 0; wave < waveCount; wave++) {
                                                double progress = wave / (double) (waveCount - 1);
                                                double radius = Mth.lerp(progress, baseRadius, maxRadius);
                                                double verticalScale = 1.8 * Math.sin(progress * Math.PI); // 增强垂直波动

                                                for (int i = 0; i < particlesPerWave; i++) {
                                                    double angle = 2 * Math.PI * i / particlesPerWave;
                                                    double randomSpread = 0.5 * (level.random.nextDouble() - 0.5);

                                                    // 粒子位置计算（带螺旋效果）
                                                    Vec3 pos = new Vec3(
                                                            radius * Math.cos(angle + progress * 1.5 * Math.PI) + randomSpread,
                                                            verticalScale * Math.sin(angle * 3 + wave * 0.7),
                                                            radius * Math.sin(angle + progress * 1.5 * Math.PI) + randomSpread
                                                    ).add(centerX, centerY, centerZ);

                                                    // 动态速度（向外扩散）
                                                    Vec3 motion = pos.subtract(centerX, centerY, centerZ).normalize()
                                                            .scale(speed * (0.4 + 0.6 * (1 - progress)))
                                                            .add(0, 0.2, 0);

                                                    // 核心火焰粒子
                                                    level.sendParticles(
                                                            ModParticle.PHANTOM_WING_FLAME.get(),
                                                            pos.x, pos.y, pos.z,
                                                            2, // 每组2个粒子
                                                            motion.x * 0.4, motion.y * 0.9, motion.z * 0.4,
                                                            1.0
                                                    );

                                                    // 50%概率生成附加火星
                                                    if (level.random.nextDouble() < 0.5) {
                                                        level.sendParticles(
                                                                ParticleTypes.ENCHANT,
                                                                pos.x, pos.y + 0.3, pos.z,
                                                                1,
                                                                motion.x * 1.5, motion.y * 2.0, motion.z * 1.5,
                                                                0.5
                                                        );
                                                    }
                                                }
                                            }

                                            // 中心爆炸强化
                                            level.sendParticles(
                                                    ModParticle.PHANTOM_WING_FLAME.get(),
                                                    centerX, centerY + 0.5, centerZ,
                                                    30,
                                                    1.8, 0.8, 1.8,
                                                    0.9
                                            );

                                            level.sendParticles(
                                                    ParticleTypes.ENCHANT,
                                                    centerX, centerY + 0.5, centerZ,
                                                    80,
                                                    2.0, 1.0, 2.0,
                                                    0.8
                                            );
                                        }
                                    }, AnimationEvent.Side.SERVER),
                                    AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, animation, params) -> {
                                        LivingEntity entity = entityPatch.getOriginal();
                                        entity.level().addParticle(AvalonParticles.AVALON_ENTITY_AFTER_IMAGE.get()
                                                , entity.getX()
                                                , entity.getY()
                                                , entity.getZ()
                                                , Double.longBitsToDouble(entity.getId())
                                                , 0, 0);
                                    }, AnimationEvent.Side.CLIENT),
                                    AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, self, params) -> {
                                        entityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 5, false, false, false));
                                    }, AnimationEvent.Side.BOTH),
                                    AnimationEvent.InTimeEvent.create(0.2F, (entityPatch, self, params) -> {
                                        entityPatch.playSound(SoundEvents.RESPAWN_ANCHOR_DEPLETE.get(), 150, 0, 0);
                                    }, AnimationEvent.Side.CLIENT),
                                    AnimationEvent.InTimeEvent.create(1.1F, (entityPatch, self, params) -> {
                                        entityPatch.playSound(SoundEvents.GENERIC_EXPLODE, 150, 0, 0);
                                    }, AnimationEvent.Side.CLIENT),
                                    AvalonEventUtils.simpleGroundSplit(57, 0, 0, 0, 0, 5F, true),
                                    AvalonEventUtils.simpleCameraShake(59, 60, 4, 4, 4),
                                    AnimationEvent.InTimeEvent.create(0.9F, (entityPatch, self, params) -> {
                                        StrikeWindmillHalberd(entityPatch.getOriginal().level(), entityPatch.getOriginal(), 3, 3, 1.0F, 1.0F, 0.4, 1);
                                        Level world = entityPatch.getOriginal().level();
                                        LivingEntity caster = entityPatch.getOriginal();
                                        for(Entity entity : world.getEntities(caster, caster.getBoundingBox().inflate(6, 6, 6))) {
                                            if (entity instanceof LivingEntity living && living.isAlive()) {
                                                living.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), 30, 0));
                                            }
                                        }
                                    }, AnimationEvent.Side.BOTH),
                                    AnimationEvent.InTimeEvent.create(1.2F, (entityPatch, self, params) -> {
                                        StrikeWindmillHalberd(entityPatch.getOriginal().level(), entityPatch.getOriginal(), 5, 3, 1.0F, 1.0F, 0.4, 1);
                                    }, AnimationEvent.Side.BOTH),
                                    AnimationEvent.InTimeEvent.create(1.5F, (entityPatch, self, params) -> {
                                        StrikeWindmillHalberd(entityPatch.getOriginal().level(), entityPatch.getOriginal(), 5, 5, 1.4F, 1.4F, 0.4, 1);
                                        entityPatch.playSound(EpicFightSounds.BIG_ENTITY_MOVE.get(), 1.0F, 1.0F);
                                    }, AnimationEvent.Side.BOTH)
                            )
                            .newTimePair(0.0F, Float.MAX_VALUE)
                            .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                            .addStateRemoveOld(EntityState.CAN_SWITCH_HAND_ITEM, false)
                            .addStateRemoveOld(EntityState.INACTION, true)
                    );

        });
    }


    public static void StrikeWindmillHalberd(Level level, LivingEntity player, int numberOfBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay) {
        float angleIncrement = (float)((Math.PI * 2D) / (double)numberOfBranches);

        for(int branch = 0; branch < numberOfBranches; ++branch) {
            float baseAngle = angleIncrement * (float)branch;

            for(int i = 0; i < particlesPerBranch; ++i) {
                double currentRadius = initialRadius + (double)i * radiusIncrement;
                float currentAngle = (float)((double)baseAngle + (double)((float)i * angleIncrement) / initialRadius + (double)((float)((double)i * curveFactor)));
                double xOffset = currentRadius * Math.cos(currentAngle);
                double zOffset = currentRadius * Math.sin(currentAngle);
                double spawnX = player.getX() + xOffset;
                double spawnY = player.getY() + 0.3;
                double spawnZ = player.getZ() + zOffset;
                int d3 = delay * (i + 1);
                double deltaX = level.getRandom().nextGaussian() * 0.007;
                double deltaY = level.getRandom().nextGaussian() * 0.007;
                double deltaZ = level.getRandom().nextGaussian() * 0.007;
                if (level.isClientSide) {
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }

                spawnHalberd(spawnX, spawnZ, player.getY() - 5.0F, player.getY() + 3.0F, currentAngle, d3, level, player);
            }
        }

    }


    public static void spawnHalberd(double x, double z, double minY, double maxY, float rotation, int delay, Level world, LivingEntity player) {
        BlockPos blockpos = BlockPos.containing(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0F;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = world.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
                if (!world.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            world.addFreshEntity(new Phantom_Halberd_Entity(world, x, (double)blockpos.getY() + d0, z, rotation, delay, player, (float)CMConfig.PhantomHalberddamage));
        }

    }

    public static AnimationEvent.E0 shootPhantomArrow(float damageRate) {
        return shootPhantomArrow(damageRate, true);
    }

    public static AnimationEvent.E0 shootPhantomArrow(float damageRate, boolean shouldClear) {
        return (entityPatch, animation, params) -> {
            LivingEntity living = entityPatch.getOriginal();
            LivingEntity target = entityPatch.getTarget();
            if (target == null) {
                if (!entityPatch.getCurrentlyAttackTriedEntities().isEmpty() && entityPatch.getCurrentlyAttackTriedEntities().get(0) instanceof LivingEntity living1) {
                    target = living1;
                }
            }
            Level level = living.level();
            Phantom_Arrow_Entity hommingArrowEntity = new Phantom_Arrow_Entity(level, living, target);
            float base = (float) entityPatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE);
            hommingArrowEntity.setBaseDamage(entityPatch.getModifiedBaseDamage(base) * damageRate);
            hommingArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
            hommingArrowEntity.setCritArrow(true);
            Vec3 playerPosition = living.position();
            float finalYal = entityPatch.getCameraYRot();
            if (target != null) {
                Vec3 targetPosition = target.position();
                finalYal = (float) MathUtils.getYRotOfVector(targetPosition.subtract(playerPosition));
            }
            hommingArrowEntity.shootFromRotation(living, 0, finalYal, 0.0F, 3.3F, 1.0F);
            hommingArrowEntity.setPos(getJointWorldPos(entityPatch, Armatures.BIPED.get().toolL));
            level.addFreshEntity(hommingArrowEntity);
            level.playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + damageRate * 0.4F);
            entityPatch.getOriginal().stopUsingItem();
            if (shouldClear) {
                entityPatch.removeHurtEntities();
            }
        };
    }

    public static AnimationEvent.E0 shootSandstormByView(float damageRate, boolean shouldClear) {
        return (entityPatch, animation, params) -> {
            LivingEntity self = entityPatch.getOriginal();
            Level level = self.level();
            Vec3 startPos = getJointWorldPos(entityPatch, Armatures.BIPED.get().toolL);
            float d7 = entityPatch.getYRot();
            float xRot = entityPatch.getOriginal().getViewXRot(1.0F);
            for (int j = -1; j <= 1; ++j) {
                float yaw = d7 + (float) (j * 15);
                float directionX = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(xRot * ((float) Math.PI / 180F));
                float directionY = -Mth.sin(xRot * ((float) Math.PI / 180F));
                float directionZ = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(xRot * ((float) Math.PI / 180F));
                Sandstorm_Projectile sandstormProjectile = new Sandstorm_Projectile(self, directionX, directionY, directionZ, self.level(), 6.0F);
                sandstormProjectile.setState(1);
                sandstormProjectile.setPos(startPos);
                level.addFreshEntity(sandstormProjectile);
            }
            level.playSound(null, self.getX(), self.getY(), self.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + damageRate * 0.4F);
            entityPatch.getOriginal().stopUsingItem();
            if (shouldClear) {
                entityPatch.removeHurtEntities();
            }
        };
    }

    public static AnimationEvent.E0 shootSandstorm(float damageRate, boolean shouldClear) {
        return (entityPatch, animation, params) -> {
            LivingEntity self = entityPatch.getOriginal();
            Level level = self.level();
            Vec3 startPos = getJointWorldPos(entityPatch, Armatures.BIPED.get().toolL);
            float d7 = entityPatch.getYRot();
            float xRot = -3;
            for (int j = -1; j <= 1; ++j) {
                float yaw = d7 + (float) (j * 15);
                float directionX = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(xRot * ((float) Math.PI / 180F));
                float directionY = -Mth.sin(xRot * ((float) Math.PI / 180F));
                float directionZ = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(xRot * ((float) Math.PI / 180F));
                Sandstorm_Projectile sandstormProjectile = new Sandstorm_Projectile(self, directionX, directionY, directionZ, self.level(), 6.0F);
                sandstormProjectile.setState(1);
                sandstormProjectile.setPos(startPos);
                level.addFreshEntity(sandstormProjectile);
            }
            level.playSound(null, self.getX(), self.getY(), self.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + damageRate * 0.4F);
            entityPatch.getOriginal().stopUsingItem();
            if (shouldClear) {
                entityPatch.removeHurtEntities();
            }
        };
    }

    public static AnimationEvent.E0 shootCursedSandstorm(float damageRate, boolean shouldClear) {
        return (entityPatch, animation, params) -> {
            LivingEntity self = entityPatch.getOriginal();
            LivingEntity target = entityPatch.getTarget();
            if (target == null) {
                if (!entityPatch.getCurrentlyAttackTriedEntities().isEmpty() && entityPatch.getCurrentlyAttackTriedEntities().get(0) instanceof LivingEntity living1) {
                    target = living1;
                }
            }
            Level level = self.level();
            float baseYaw = entityPatch.getYRot();
            float pitch = -3;
            Vec3 startPos = getJointWorldPos(entityPatch, Armatures.BIPED.get().toolL);
            for (int j = -1; j <= 1; ++j) {
                float yaw = baseYaw + (float) (j * 15);
                float directionX = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
                float directionY = -Mth.sin(pitch * ((float) Math.PI / 180F));
                float directionZ = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));

                if (target != null && !target.isAlliedTo(self)) {
                    Cursed_Sandstorm_Entity cursedSandstormEntity = new Cursed_Sandstorm_Entity(self, directionX, directionY, directionZ, level, (float) CMConfig.CursedSandstormDamage * damageRate, target);
                    cursedSandstormEntity.setPos(startPos);
                    cursedSandstormEntity.setUp(15);
                    level.addFreshEntity(cursedSandstormEntity);
                    continue;
                }

                Cursed_Sandstorm_Entity cursedSandstormEntity = new Cursed_Sandstorm_Entity(self, directionX, directionY, directionZ, level, (float) CMConfig.CursedSandstormDamage * damageRate, null);
                cursedSandstormEntity.setPos(startPos);
                cursedSandstormEntity.setUp(15);
                level.addFreshEntity(cursedSandstormEntity);
            }
            level.playSound(null, self.getX(), self.getY(), self.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + damageRate * 0.4F);
            entityPatch.getOriginal().stopUsingItem();
            if (shouldClear) {
                entityPatch.removeHurtEntities();
            }
        };
    }

    private static void roarParticle(LivingEntity entity, float vec, float math, float y, int duration, int r, int g, int b, float a, float start, float inc, float end) {
        if (entity.level().isClientSide) {
            float f = Mth.cos(entity.yBodyRot * ((float)Math.PI / 180F));
            float f1 = Mth.sin(entity.yBodyRot * ((float)Math.PI / 180F));
            double theta = (double)entity.yBodyRot * (Math.PI / 180D);
            ++theta;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            entity.level().addParticle(new RoarParticle.RoarData(duration, r, g, b, a, start, inc, end), entity.getX() + (double)vec * vecX + (double)(f * math), entity.getY() + (double)y, entity.getZ() + (double)vec * vecZ + (double)(f1 * math), (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    public static AnimationEvent.E0 shootThreeStorm(boolean shouldClear) {
        return (entityPatch, animation, params) -> {
            LivingEntity self = entityPatch.getOriginal();
            Level level = self.level();
            for(int i = 0; i < 3; ++i) {
                float angle = (float)i * (float)Math.PI / 1.5F;
                double sx = self.getX() + (double)(Mth.cos(angle) * 8.0F);
                double sy = self.getY();
                double sz = self.getZ() + (double)(Mth.sin(angle) * 8.0F);
                if (!self.level().isClientSide()) {
                    Sandstorm_Entity projectile = new Sandstorm_Entity(self.level(), sx, sy, sz, 140, angle, self);
                    self.level().addFreshEntity(projectile);
                }
            }
            level.playSound(null, self.getX(), self.getY(), self.getZ(), ModSounds.SANDSTORM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            if (shouldClear) {
                entityPatch.removeHurtEntities();
            }
        };
    }
    
    public static AnimationEvent.E0 shootRain(boolean shouldClear) {
        return (entityPatch, animation, params) -> {
            LivingEntity self = entityPatch.getOriginal();
            LivingEntity target = entityPatch.getTarget();
            Level level = self.level();
            double d1;
            if(target != null) {
                d1 = target.getY();
            } else {
                d1 = self.getY();
            }
            float f = (float)Mth.atan2(target != null ? (target.getZ() - self.getZ()) : 0, target != null ? target.getX() - self.getX() : 0);
            StrikeWindmillMonolith(self, 8, 16, 2.0F, 0.75F, 0.6, d1, 1);

            for(int l = 0; l < 16; ++l) {
                double d2 = (double)1.25F * (double)(l + 1);
                int j = (int)(5.0F + 1.5F * (float)l);
                spawnSpikeLine(self, self.getX() + (double)Mth.cos(f) * d2, self.getZ() + (double)Mth.sin(f) * d2, d1, f, j);
            }
            level.playSound(null, self.getX(), self.getY(), self.getZ(), ModSounds.IGNIS_EARTHQUAKE.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            if (shouldClear) {
                entityPatch.removeHurtEntities();
            }
        };
    }

    private static void StrikeWindmillMonolith(LivingEntity entity, int numberOfBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, double spawnY, int delay) {
        float angleIncrement = (float)((Math.PI * 2D) / (double)numberOfBranches);

        for(int branch = 0; branch < numberOfBranches; ++branch) {
            float baseAngle = angleIncrement * (float)branch;

            for(int i = 0; i < particlesPerBranch; ++i) {
                double currentRadius = initialRadius + (double)i * radiusIncrement;
                float currentAngle = (float)((double)baseAngle + (double)((float)i * angleIncrement) / initialRadius + (double)((float)((double)i * curveFactor)));
                double xOffset = currentRadius * Math.cos(currentAngle);
                double zOffset = currentRadius * Math.sin(currentAngle);
                double spawnX = entity.getX() + xOffset;
                double spawnZ = entity.getZ() + zOffset;
                int d3 = delay * (i + 1);
                spawnSpikeLine(entity, spawnX, spawnZ, spawnY, currentAngle, d3);
            }
        }

    }

    private static void spawnSpikeLine(LivingEntity entity, double posX, double posZ, double posY, float rotation, int delay) {
        BlockPos blockpos = BlockPos.containing(posX, posY, posZ);
        double d0 = 0.0F;

        do {
            BlockPos blockpos1 = blockpos.above();
            BlockState blockstate = entity.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(entity.level(), blockpos1, Direction.DOWN)) {
                if (!entity.level().isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = entity.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(entity.level(), blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }
                break;
            }

            blockpos = blockpos.above();
        } while(blockpos.getY() < Math.min(entity.level().getMaxBuildHeight(), entity.getBlockY() + 20));

        entity.level().addFreshEntity(new Ancient_Desert_Stele_Entity(entity.level(), posX, (double)blockpos.getY() + d0 - (double)3.0F, posZ, rotation, delay, (float)CMConfig.AncientDesertSteledamage, entity));
    }

    public static AnimationEvent.E0 shootCeraunus() {
        return (entityPatch, animation, params) -> {
            LivingEntity living = entityPatch.getOriginal();
            if (living.getMainHandItem().getItem() instanceof Ceraunus) {
                Level level = living.level();
                Player_Ceraunus_Entity launchedBlock = new Player_Ceraunus_Entity(level, living);
                launchedBlock.setBaseDamage((float) living.getAttributeValue(Attributes.ATTACK_DAMAGE) + 15);
                float xRot = living.getXRot() - 5;
                if (xRot < -5) {
                    xRot = -5;
                } else if (xRot > 20) {
                    xRot = 20;
                }
                launchedBlock.shootFromRotation(living, xRot, entityPatch.getYRot(), 0.0F, 2.5F, 1.0F);
                if (level.addFreshEntity(launchedBlock)) {
                    living.getMainHandItem().getOrCreateTag().putUUID("thrown_anchor", launchedBlock.getUUID());
                }
                level.playSound(null, living.getX(), living.getY(), living.getZ(), EpicFightSounds.ENTITY_MOVE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                entityPatch.removeHurtEntities();
            }
        };
    }

    public static Vec3 getJointWorldPos(LivingEntityPatch<?> entityPatch, Joint joint) {
        Animator animator = entityPatch.getAnimator();
        Armature armature = entityPatch.getArmature();

        Pose pose = animator.getPose(0.5f);

        Vec3 pos = entityPatch.getOriginal().position();
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float) pos.x, (float) pos.y, (float) pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entityPatch.getModelMatrix(1)));

        OpenMatrix4f JointTf = new OpenMatrix4f(armature.getBoundTransformFor(pose, joint)).mulFront(modelTf);

        return OpenMatrix4f.transform(JointTf, Vec3.ZERO);
    }

}

package com.p1nero.tcrcore.gameassets;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Cursed_Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Player_Ceraunus_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.github.L_Ender.cataclysm.items.Ceraunus;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.animations.ScanAttackAnimation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRAnimations {
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_B;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_F;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_L;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_R;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_1;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_2;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_3;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_RAIN;

    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_SKILL1;
    public static AnimationManager.AnimationAccessor<ScanAttackAnimation> BOW_SKILL2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> CERAUNUS_SKILL1;

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

            BOW_SKILL1 = builder.nextAccessor("bow/bow_skill1", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 0.5F, 0.5F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .newTimePair(0.0F, 1.1F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InTimeEvent.create(0.49F,
                                    shootSandstorm(0.8F, true), AnimationEvent.Side.SERVER))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));

            BOW_SKILL2 = builder.nextAccessor("bow/bow_skill2", (accessor) ->
                    new ScanAttackAnimation(0.15F, 0.0F, 0.15F, 1.1F, 1.2F,
                            InteractionHand.MAIN_HAND, TCRWeaponPresets.BOW_SCAN, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                            .newTimePair(0.0F, 1.1F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InTimeEvent.create(1.1F,
                                    shootCursedSandstorm(0.8F, true), AnimationEvent.Side.SERVER))
                            .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, ((dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.0F)));

            CERAUNUS_SKILL1 = builder.nextAccessor("skill/arc_auto1", (accessor) ->
                    new ActionAnimation(0.15F, 1.5F, accessor, Armatures.BIPED)
                            .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                            .newTimePair(0.0F, 1.5F)
                            .addStateRemoveOld(EntityState.ATTACK_RESULT, (damageSource -> AttackResult.ResultType.BLOCKED))
                            .addEvents(AnimationEvent.InTimeEvent.create(0.5F, shootCeraunus(), AnimationEvent.Side.SERVER)));

        });
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

    public static AnimationEvent.E0 shootSandstorm(float damageRate, boolean shouldClear) {
        return (entityPatch, animation, params) -> {
            LivingEntity self = entityPatch.getOriginal();
            Level level = self.level();
            Vec3 startPos = getJointWorldPos(entityPatch, Armatures.BIPED.get().toolL);
            float d7 = entityPatch.getYRot();
            float xRot = entityPatch.getCameraXRot();
            if(xRot < -5) {
                xRot = -5;
            } else if(xRot > 20) {
                xRot = 20;
            }
            float d1 = -Mth.sin(d7 * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
            float d2 = -Mth.sin(xRot * ((float)Math.PI / 180F));
            float d3 = Mth.cos(d7 * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
            Sandstorm_Projectile largefireball = new Sandstorm_Projectile(self, d1, d2, d3, self.level(), 6.0F);
            largefireball.setState(1);
            largefireball.setPos(startPos);
            level.addFreshEntity(largefireball);
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
            for(int j = -1; j <= 1; ++j) {
                float yaw = baseYaw + (float)(j * 15);
                float directionX = -Mth.sin(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));
                float directionY = -Mth.sin(pitch * ((float)Math.PI / 180F));
                float directionZ = Mth.cos(yaw * ((float)Math.PI / 180F)) * Mth.cos(pitch * ((float)Math.PI / 180F));

                if (target != null && !target.isAlliedTo(self)) {
                    Cursed_Sandstorm_Entity largefireball = new Cursed_Sandstorm_Entity(self, directionX, directionY, directionZ, level, (float) CMConfig.CursedSandstormDamage * damageRate, target);
                    largefireball.setPos(startPos);
                    largefireball.setUp(15);
                    level.addFreshEntity(largefireball);
                    continue;
                }

                Cursed_Sandstorm_Entity largefireball = new Cursed_Sandstorm_Entity(self, directionX, directionY, directionZ, level, (float)CMConfig.CursedSandstormDamage * damageRate, null);
                largefireball.setPos(startPos);
                largefireball.setUp(15);
                level.addFreshEntity(largefireball);
            }
            level.playSound(null, self.getX(), self.getY(), self.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + damageRate * 0.4F);
            entityPatch.getOriginal().stopUsingItem();
            if (shouldClear) {
                entityPatch.removeHurtEntities();
            }
        };
    }

    public static AnimationEvent.E0 shootCeraunus() {
        return (entityPatch, animation, params) -> {
            LivingEntity living = entityPatch.getOriginal();
            if(living.getMainHandItem().getItem() instanceof Ceraunus) {
                Level level = living.level();
                Player_Ceraunus_Entity launchedBlock = new Player_Ceraunus_Entity(level, living);
                launchedBlock.setBaseDamage((float)living.getAttributeValue(Attributes.ATTACK_DAMAGE));
                float xRot = living.getXRot() - 5;
                if(xRot < -5) {
                    xRot = -5;
                } else if(xRot > 20) {
                    xRot = 20;
                }
                launchedBlock.shootFromRotation(living, xRot , entityPatch.getYRot(), 0.0F, 2.5F, 1.0F);
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

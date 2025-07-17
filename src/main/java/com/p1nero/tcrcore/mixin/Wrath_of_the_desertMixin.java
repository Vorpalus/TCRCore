package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.items.Wrath_of_the_desert;
import com.p1nero.tcrcore.animations.ScanAttackAnimation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

import static com.github.L_Ender.cataclysm.items.Wrath_of_the_desert.getUseTime;

@Mixin(Wrath_of_the_desert.class)
public abstract class Wrath_of_the_desertMixin extends Item {
    public Wrath_of_the_desertMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Shadow(remap = false) protected abstract Entity getPlayerLookTarget(Level level, LivingEntity living);

    @Shadow(remap = false)
    public static float getPowerForTime(int i) {
        return 0;
    }

    @Shadow public abstract int getUseDuration(@NotNull ItemStack stack);

    @Shadow(remap = false)
    public static void setUseTime(ItemStack stack, int useTime) {
    }


    @Shadow(remap = false)
    private static int getMaxLoadTime() {
        return 0;
    }

    @Inject(method = "inventoryTick", at = @At("HEAD"), cancellable = true)
    private void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean held, CallbackInfo ci) {
        super.inventoryTick(stack, level, entity, i, held);
        boolean using = entity instanceof LivingEntity living && living.getUseItem().equals(stack);
        int useTime = getUseTime(stack);
        if (level.isClientSide) {

            CompoundTag tag = stack.getOrCreateTag();
            if (tag.getInt("PrevUseTime") != tag.getInt("UseTime")) {
                tag.putInt("PrevUseTime", getUseTime(stack));
            }

            LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
            if(localPlayerPatch != null && localPlayerPatch.getEntityState().attacking()) {
                AnimationPlayer animationPlayer = localPlayerPatch.getAnimator().getPlayerFor(null);
                if(animationPlayer != null && animationPlayer.getAnimation().get() instanceof ScanAttackAnimation) {
                    setUseTime(stack, (int) (animationPlayer.getElapsedTime() * 40.0F));
                }
            } else {
                int maxLoadTime = getMaxLoadTime();
                if (using && useTime < maxLoadTime) {
                    int set = useTime + 1;
                    setUseTime(stack, set);
                }
            }
        }
        if (!using && useTime > 0.0F) {
            setUseTime(stack, Math.max(0, useTime - 5));
        }
        ci.cancel();
    }

    /**
     * 改为一根箭且削弱伤害
     */
    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    private void tcr$releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeleft, CallbackInfo ci) {
        if (living instanceof Player player) {
            Entity pointedEntity = getPlayerLookTarget(level, living);
            int i = this.getUseDuration(stack) - timeleft;
            float f = getPowerForTime(i);
            if (f >= 0.3) {
                if (!level.isClientSide) {
//                    float baseYaw = player.getYRot();
//                    float pitch = player.getXRot();
//                    for (int j = -1; j <= 1; j++) {
//                        float yaw = baseYaw + (j * 15);
//                        float directionX = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
//                        float directionY = -Mth.sin(pitch * ((float) Math.PI / 180F));
//                        float directionZ = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
//                        double theta = yaw * (Math.PI / 180); theta += Math.PI / 2;
//                        double vecX = Math.cos(theta);
//                        double vecZ = Math.sin(theta);
//                        double x = player.getX() + vecX;
//                        double Z = player.getZ() + vecZ;
//                        int p = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
//                        if (pointedEntity instanceof LivingEntity target && !target.isAlliedTo(living)) {
//                            Cursed_Sandstorm_Entity largefireball = new Cursed_Sandstorm_Entity(player, directionX, directionY, directionZ, player.level(), (float) CMConfig.CursedSandstormDamage * f + (float) CMConfig.CursedSandstormDamage * f * p * 0.05F, target);
//                            largefireball.setPos(x, player.getEyeY() - 0.5D, Z);
//                            largefireball.setUp(15);
//                            level.addFreshEntity(largefireball);
//                        }else{
//                            Cursed_Sandstorm_Entity largefireball = new Cursed_Sandstorm_Entity(player, directionX, directionY, directionZ, player.level(), (float) CMConfig.CursedSandstormDamage * f + (float) CMConfig.CursedSandstormDamage * f * p * 0.05F, null);
//                            largefireball.setPos(x, player.getEyeY() - 0.5D, Z);
//                            largefireball.setUp(15);
//                            level.addFreshEntity(largefireball);
//                        }
//                    }

                    for (int j = 0; j < 3; j++) {
                        int p = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        AbstractArrow phantomArrowEntity;
                        if (pointedEntity instanceof LivingEntity target && !target.isAlliedTo(living)) {
                            Phantom_Arrow_Entity hommingArrowEntity = new Phantom_Arrow_Entity(level, living, target);
                            hommingArrowEntity.setBaseDamage(2 * f);
                            if (p > 0) {
                                hommingArrowEntity.setBaseDamage(hommingArrowEntity.getBaseDamage() + (double)p * 0.35D + 0.5D);
                            }
                            phantomArrowEntity = hommingArrowEntity;
                        } else {
                            Phantom_Arrow_Entity hommingArrowEntity = new Phantom_Arrow_Entity(level, living);
                            hommingArrowEntity.setBaseDamage(2 * f);
                            if (p > 0) {
                                hommingArrowEntity.setBaseDamage(hommingArrowEntity.getBaseDamage() + (double)p * 0.35D + 0.5D);
                            }
                            phantomArrowEntity = hommingArrowEntity;
                        }
                        phantomArrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;

                        phantomArrowEntity.shootFromRotation(player, player.getXRot(), player.getYRot() + (j - (3 - 1) / 2.0F) * 12, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            phantomArrowEntity.setCritArrow(true);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (k > 0) {
                            phantomArrowEntity.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            phantomArrowEntity.setSecondsOnFire(100);
                        }
                        level.addFreshEntity(phantomArrowEntity);
                    }


                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    player.awardStat(Stats.ITEM_USED.get((Wrath_of_the_desert)(Object)this));
                }
            }
        }
        ci.cancel();
    }
}

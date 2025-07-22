package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.items.Wrath_of_the_desert;
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

    /**
     * 改为箭且削弱伤害
     */
    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    private void tcr$releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeleft, CallbackInfo ci) {
        if (living instanceof Player player) {
            Entity pointedEntity = getPlayerLookTarget(level, living);
            int i = this.getUseDuration(stack) - timeleft;
            float f = getPowerForTime(i);
            if (f >= 0.3) {
                if (!level.isClientSide) {

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

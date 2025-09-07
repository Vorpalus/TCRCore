package com.p1nero.tcrcore.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.unusual.blockfactorysbosses.item.KnightSwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KnightSwordItem.class)
public abstract class KnightSwordItemMixin extends SwordItem {

    public KnightSwordItemMixin(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Inject(method = "onEntitySwing", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$onSwing(ItemStack itemstack, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.onEntitySwing(itemstack, entity));
    }
}

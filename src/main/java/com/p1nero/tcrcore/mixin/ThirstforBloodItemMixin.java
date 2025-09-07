package com.p1nero.tcrcore.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.sonmok14.fromtheshadows.server.items.ThirstforBloodItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThirstforBloodItem.class)
public abstract class ThirstforBloodItemMixin extends SwordItem {
    public ThirstforBloodItemMixin(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
    private void tcr$getUseAnim(ItemStack p_41452_, CallbackInfoReturnable<UseAnim> cir) {
        cir.setReturnValue(UseAnim.SPEAR);
    }
}

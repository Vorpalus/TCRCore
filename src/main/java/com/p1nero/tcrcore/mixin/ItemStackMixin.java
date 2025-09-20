package com.p1nero.tcrcore.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 移除物品耐久度
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "isDamageableItem", at = @At("HEAD"), cancellable = true)
    private void tcr$damageable(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}

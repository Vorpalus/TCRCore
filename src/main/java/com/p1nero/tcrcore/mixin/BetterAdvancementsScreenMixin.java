package com.p1nero.tcrcore.mixin;

import betteradvancements.common.gui.BetterAdvancementsScreen;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BetterAdvancementsScreen.class)
public abstract class BetterAdvancementsScreenMixin extends Screen {

    protected BetterAdvancementsScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    /**
     * 只留自己的进度
     */
    @Inject(method = "onAddAdvancementRoot", at = @At("HEAD"), cancellable = true, remap = false)
    private void smc$onAddAdvancementRoot(Advancement advancement, CallbackInfo ci){
        if(!(advancement.getId().getNamespace().equals(TCRCoreMod.MOD_ID))){
            ci.cancel();
        }
    }

    /**
     * 加个按钮音效
     */
    @Inject(method = "onSelectedTabChanged", at = @At("HEAD"))
    private void smc$onSelectedTabChanged(Advancement p_97391_, CallbackInfo ci){
        if(this.minecraft != null && this.minecraft.player != null){
            LocalPlayer localPlayer = this.minecraft.player;
            localPlayer.playSound(SoundEvents.UI_BUTTON_CLICK.get());
        }
    }

}

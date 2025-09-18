package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.capability.PlayerDataManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

@Mixin(LocalPlayerPatch.class)
public class LocalPlayerPatchMixin {

    @Inject(method = "setLockOn", at = @At("HEAD"), remap = false)
    private void tcr$setLockOn(boolean targetLockedOn, CallbackInfo ci) {
        if(targetLockedOn && Minecraft.getInstance().player != null) {
            PlayerDataManager.locked.put(Minecraft.getInstance().player, true);
        }
    }
}

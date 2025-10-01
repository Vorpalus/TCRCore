package com.p1nero.tcrcore.mixin;

import net.minecraft.world.item.Items;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.config.ClientConfig;

@Mixin(ClientConfig.class)
public class EFClientConfigMixin {

    @Inject(method = "onLoad", at = @At("TAIL"), remap = false)
    private static void onModLoad(ModConfigEvent event, CallbackInfo ci) {
        if(event.getConfig().getType() == ModConfig.Type.CLIENT){
            ClientConfig.combatPreferredItems.remove(Items.AIR);
            ClientConfig.miningPreferredItems.add(Items.AIR);
        }
    }
}

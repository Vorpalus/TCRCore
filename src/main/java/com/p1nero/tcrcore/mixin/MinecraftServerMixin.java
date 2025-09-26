//package com.p1nero.tcrcore.mixin;
//
//import com.p1nero.tcrcore.events.ServerEvents;
//import net.minecraft.server.MinecraftServer;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(MinecraftServer.class)
//public class MinecraftServerMixin {
//
//    @Inject(method = "loadLevel", at = @At("HEAD"))
//    private void tcr$loadLevel(CallbackInfo ci) {
//        ServerEvents.copyDuelDirectory((MinecraftServer) (Object) this);
//    }
//}

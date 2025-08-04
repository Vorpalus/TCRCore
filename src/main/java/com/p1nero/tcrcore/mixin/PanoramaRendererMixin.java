package com.p1nero.tcrcore.mixin;

import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PanoramaRenderer.class)
public class PanoramaRendererMixin {

    @Shadow private float spin;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void tcr$init(CubeMap p_110002_, CallbackInfo ci) {
        this.spin = 270;
    }
}

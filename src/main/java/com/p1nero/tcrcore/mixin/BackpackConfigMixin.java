package com.p1nero.tcrcore.mixin;

import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Config.Server.BackpackConfig.class)
public class BackpackConfigMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ForgeConfigSpec$Builder;defineInRange(Ljava/lang/String;III)Lnet/minecraftforge/common/ForgeConfigSpec$IntValue;"), index = 3, remap = false)
    private int tcr$modifyLimit(int max) {
        return 114514;
    }
}

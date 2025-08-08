package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ancient_Remnant_Entity.class)
public abstract class Ancient_Remnant_EntityMixin extends IABoss_monster {

    public Ancient_Remnant_EntityMixin(EntityType entity, Level world) {
        super(entity, world);
    }

    @Shadow(remap = false) public abstract boolean getNecklace();

    @Shadow(remap = false) public abstract void setNecklace(boolean necklace);

    /**
     * 直接唤醒
     */
    @Inject(method = "mobInteract" , at = @At("HEAD"), cancellable = true)
    private void tcr$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if(!this.getNecklace()) {
            this.setHomePos(this.blockPosition());
            this.setNecklace(true);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
        cir.setReturnValue(InteractionResult.CONSUME);
    }

}

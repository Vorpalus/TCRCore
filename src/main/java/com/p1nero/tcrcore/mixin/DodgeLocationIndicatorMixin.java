package com.p1nero.tcrcore.mixin;

import com.p1nero.dialog_lib.network.PacketRelay;
import com.p1nero.tcrcore.capability.TCRPlayer;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.AddAvlEntityAfterImageParticle;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.DodgeLocationIndicator;

/**
 * 完美闪避的一些全局设定
 */
@Mixin(value = DodgeLocationIndicator.class)
public abstract class DodgeLocationIndicatorMixin extends LivingEntity {

    @Shadow(remap = false) private LivingEntityPatch<?> entitypatch;

    protected DodgeLocationIndicatorMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "hurt", at = @At("HEAD"), remap = false)
    private void tcr$hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir){
        if(this.entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
            TCRPlayer.addSkillPoint(serverPlayerPatch.getOriginal());
            ServerPlayer serverPlayer = serverPlayerPatch.getOriginal();
            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new AddAvlEntityAfterImageParticle(serverPlayer.getId()), serverPlayer);
            serverPlayerPatch.getOriginal().connection.send(new ClientboundSoundPacket(EpicFightSounds.ENTITY_MOVE.getHolder().orElseThrow(), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
        }
    }
}

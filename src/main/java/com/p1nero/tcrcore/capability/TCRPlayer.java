package com.p1nero.tcrcore.capability;

import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.OpenEndScreenPacket;
import com.p1nero.tcrcore.network.packet.clientbound.SyncTCRPlayerPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * 记录飞行和技能使用的状态，被坑了，这玩意儿也分服务端和客户端...
 * 懒得换成DataKey了将就一下吧
 */
public class TCRPlayer {
    private CompoundTag data = new CompoundTag();

    private int tickAfterBossDieLeft;

    public void setTickAfterBossDieLeft(int tickAfterBossDieLeft) {
        this.tickAfterBossDieLeft = tickAfterBossDieLeft;
    }

    public int getTickAfterBossDieLeft() {
        return tickAfterBossDieLeft;
    }

    private PathfinderMob currentTalkingEntity;

    public void setCurrentTalkingEntity(@Nullable PathfinderMob currentTalkingEntity) {
        this.currentTalkingEntity = currentTalkingEntity;
    }

    public @Nullable PathfinderMob getCurrentTalkingEntity() {
        return currentTalkingEntity;
    }

    public boolean getBoolean(String key) {
        return data.getBoolean(key);
    }

    public double getDouble(String key) {
        return data.getDouble(key);
    }

    public String getString(String key) {
        return data.getString(key);
    }

    public void putBoolean(String key, boolean value) {
        data.putBoolean(key, value);
    }

    public void putDouble(String key, double v) {
        data.putDouble(key, v);
    }

    public void putString(String k, String v) {
        data.putString(k, v);
    }

    public CompoundTag getData() {
        return data;
    }
    public CompoundTag saveNBTData(CompoundTag tag) {
        tag.put("customDataManager", data);
        return tag;
    }

    public void loadNBTData(CompoundTag tag) {
        data = tag.getCompound("customDataManager");
    }

    public void copyFrom(TCRPlayer old) {
        this.data = old.data;
        this.tickAfterBossDieLeft = old.tickAfterBossDieLeft;

    }

    public void syncToClient(ServerPlayer serverPlayer) {
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new SyncTCRPlayerPacket(saveNBTData(new CompoundTag())), serverPlayer);
    }

    public void tick(Player player) {
        if(player.isLocalPlayer()) {

        } else if(player instanceof ServerPlayer serverPlayer){
            if (this.currentTalkingEntity != null && this.currentTalkingEntity.isAlive()) {
                this.currentTalkingEntity.getLookControl().setLookAt(player);
                this.currentTalkingEntity.getNavigation().stop();
                if (this.currentTalkingEntity.distanceTo(player) > 8) {
                    this.currentTalkingEntity = null;
                }
            }
            //Boss战后的返回倒计时
            if (tickAfterBossDieLeft > 0) {
                tickAfterBossDieLeft--;
                if (tickAfterBossDieLeft % 40 == 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.8F, 0.5F + tickAfterBossDieLeft / 400.0F);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 0.8F, 0.5F + tickAfterBossDieLeft / 400.0F);
                }
                player.displayClientMessage(TCRCoreMod.getInfo("second_after_boss_die_left", tickAfterBossDieLeft / 20).withStyle(ChatFormatting.BOLD, ChatFormatting.RED), true);
                if (tickAfterBossDieLeft == 0) {
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new OpenEndScreenPacket(), serverPlayer);
                }
            }
        }
    }

}

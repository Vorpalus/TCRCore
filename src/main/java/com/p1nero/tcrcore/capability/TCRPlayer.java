package com.p1nero.tcrcore.capability;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.dialog_lib.network.PacketRelay;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.SyncTCRPlayerPacket;
import com.yesman.epicskills.registry.entry.EpicSkillsSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

/**
 * 记录飞行和技能使用的状态，被坑了，这玩意儿也分服务端和客户端...
 * 懒得换成DataKey了将就一下吧
 */
public class TCRPlayer {
    public static final int MAX_SKILL_POINTS = 5;
    private CompoundTag data = new CompoundTag();

    public static boolean isValidWeapon(ItemStack itemStack) {
        return itemStack.is(ModItems.CERAUNUS.get()) || itemStack.is(ModItems.THE_INCINERATOR.get()) || itemStack.is(ModItems.SOUL_RENDER.get()) || itemStack.is(ModItems.WRATH_OF_THE_DESERT.get());
    }

    public static void addSkillPoint(ServerPlayer serverPlayer) {
        serverPlayer.heal(2);
        double current = DataManager.skillPoint.get(serverPlayer);
        if(current < TCRPlayer.MAX_SKILL_POINTS) {
            DataManager.skillPoint.put(serverPlayer, current + 1);
            serverPlayer.connection.send(new ClientboundSoundPacket(EpicSkillsSounds.GAIN_ABILITY_POINTS.getHolder().orElseThrow(), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), (float) (1.0F+ 0.1F * current), (float) (0.7F + 0.1F * current), serverPlayer.getRandom().nextInt()));
        }
    }

    public static void setSkillPoint(ServerPlayer serverPlayer, int point) {
        if(point < TCRPlayer.MAX_SKILL_POINTS && point >= 0) {
            DataManager.skillPoint.put(serverPlayer, (double) point);
            serverPlayer.connection.send(new ClientboundSoundPacket(EpicSkillsSounds.GAIN_ABILITY_POINTS.getHolder().orElseThrow(), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F+ 0.1F * point, 0.7F + 0.1F * point, serverPlayer.getRandom().nextInt()));
        }
    }

    public static int getSkillPoint(Player serverPlayer) {
        return DataManager.skillPoint.get(serverPlayer).intValue();
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

    public void setData(Consumer<CompoundTag> consumer) {
        consumer.accept(data);
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

    }

    public void syncToClient(ServerPlayer serverPlayer) {
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new SyncTCRPlayerPacket(saveNBTData(new CompoundTag())), serverPlayer);
    }

    public void tick(Player player) {

    }

}

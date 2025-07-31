package com.p1nero.tcrcore.network.packet.clientbound;

import com.p1nero.dialog_lib.network.packet.BasePacket;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.network.packet.clientbound.helper.DistHelper;
import com.p1nero.tcrcore.network.packet.clientbound.helper.TCRClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public record PersistentDoubleDataSyncPacket(String key, boolean isLocked, double value) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(key));
        buf.writeBoolean(isLocked);
        buf.writeDouble(value);
    }

    public static PersistentDoubleDataSyncPacket decode(FriendlyByteBuf buf) {
        String key = buf.readComponent().getString();
        boolean isLocked = buf.readBoolean();
        double value = buf.readDouble();
        return new PersistentDoubleDataSyncPacket(key, isLocked, value);
    }

    @Override
    public void execute(Player player) {
        if(player != null) {
            if (isLocked) {
                PlayerDataManager.putData(player, key + "isLocked", true);
                return;
            }
            PlayerDataManager.putData(player, key, value);
            PlayerDataManager.putData(player, key + "isLocked", false);
        }
        DistHelper.runClient(() -> () -> {
            TCRClientHandler.syncDoubleData(key, isLocked, value);
        });
    }
}
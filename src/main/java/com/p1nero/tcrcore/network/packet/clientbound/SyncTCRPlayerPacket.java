package com.p1nero.tcrcore.network.packet.clientbound;

import com.p1nero.dialog_lib.network.packet.BasePacket;
import com.p1nero.tcrcore.network.packet.clientbound.helper.DistHelper;
import com.p1nero.tcrcore.network.packet.clientbound.helper.TCRClientHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record SyncTCRPlayerPacket(CompoundTag data) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(data);
    }

    public static SyncTCRPlayerPacket decode(FriendlyByteBuf buf) {
        return new SyncTCRPlayerPacket(buf.readNbt());
    }

    @Override
    public void execute(Player playerEntity) {
        DistHelper.runClient(() -> () -> {
            TCRClientHandler.syncTCRPlayer(data);
        });
    }
}
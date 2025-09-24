package com.p1nero.tcrcore.network.packet.serverbound;
import com.p1nero.dialog_lib.network.packet.BasePacket;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record EndScreenCallbackPacket() implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
    }

    public static EndScreenCallbackPacket decode(FriendlyByteBuf buf) {
        return new EndScreenCallbackPacket();
    }

    @Override
    public void execute(Player player) {
        if (player instanceof ServerPlayer serverPlayer && player.getServer() != null) {
            ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
            if (overworld != null) {
                if(player.level().dimension() != Level.OVERWORLD) {
                    serverPlayer.changeDimension(overworld);
                }
            }
            player.displayClientMessage(TCRCoreMod.getInfo("to_be_continue"), false);
        }
    }
}
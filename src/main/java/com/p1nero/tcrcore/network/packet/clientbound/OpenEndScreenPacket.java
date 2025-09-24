package com.p1nero.tcrcore.network.packet.clientbound;
import com.p1nero.dialog_lib.network.packet.BasePacket;
import com.p1nero.tcrcore.network.packet.clientbound.helper.DistHelper;
import com.p1nero.tcrcore.network.packet.clientbound.helper.TCRClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record OpenEndScreenPacket() implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
    }

    public static OpenEndScreenPacket decode(FriendlyByteBuf buf) {
        return new OpenEndScreenPacket();
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DistHelper.runClient(() -> TCRClientHandler::openEndScreen);
        }
    }
}
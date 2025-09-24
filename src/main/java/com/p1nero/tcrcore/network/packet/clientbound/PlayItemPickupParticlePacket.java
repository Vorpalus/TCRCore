package com.p1nero.tcrcore.network.packet.clientbound;

import com.p1nero.dialog_lib.network.packet.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 手动加残影，server level的sendParticle发到客户端会暗改参数
 */
public record PlayItemPickupParticlePacket(ItemStack itemStack) implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(itemStack);
    }

    public static PlayItemPickupParticlePacket decode(FriendlyByteBuf buf){
        return new PlayItemPickupParticlePacket(buf.readItem());
    }

    @Override
    public void execute(Player player) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().level != null){
           Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack);
        }
    }
}

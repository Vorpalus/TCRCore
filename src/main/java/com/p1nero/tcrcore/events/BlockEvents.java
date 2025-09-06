package com.p1nero.tcrcore.events;

import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.OpenBanPortalScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class BlockEvents {
    @SubscribeEvent
    public static void onPortalBlock(BlockEvent.PortalSpawnEvent event) {
        event.setCanceled(true);
        if (!event.getLevel().isClientSide()) {
            for (Player player : event.getLevel().players()) {
                if(player.distanceToSqr(event.getPos().getCenter()) < 25) {
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new OpenBanPortalScreenPacket(), ((ServerPlayer) player));
                }
            }
        }
    }

}

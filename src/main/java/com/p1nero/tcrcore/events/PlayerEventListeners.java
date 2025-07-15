package com.p1nero.tcrcore.events;


import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.DataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class PlayerEventListeners {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if(player instanceof ServerPlayer serverPlayer) {
            TCRCapabilityProvider.syncPlayerDataToClient(serverPlayer);
            if(!DataManager.firstJoint.get(serverPlayer)) {
                CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withPermission(2);
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "gamerule keepInventory true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "gamerule mobGriefing false");
                //TODO 学习初始技能 闪避和招架
                DataManager.firstJoint.put(serverPlayer, true);
            }
            DataManager.skillPoint.put(serverPlayer, 0D);
        }
    }

}

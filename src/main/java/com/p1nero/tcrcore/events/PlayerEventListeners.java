package com.p1nero.tcrcore.events;


import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.datagen.TCRAdvancementData;
import com.p1nero.tcrcore.utils.ItemUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class PlayerEventListeners {


    @SubscribeEvent
    public static void onPlayerAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if(event.getEntity() instanceof ServerPlayer serverPlayer) {
            String path = event.getAdvancement().getId().getPath();
            String namespace = event.getAdvancement().getId().getNamespace();
            if(namespace.equals(TCRCoreMod.MOD_ID)) {
                if(path.equals("kill_pillager")) {
                    PlayerDataManager.pillagerKilled.put(serverPlayer, true);
                }
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("press_to_show_progress"), false);
            }
            if(namespace.equals("minecraft") && path.equals("recipes/transportation/oak_boat")) {
                serverPlayer.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("riptide_tutorial")));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if(player instanceof ServerPlayer serverPlayer) {
            TCRCapabilityProvider.syncPlayerDataToClient(serverPlayer);
            if(!PlayerDataManager.firstJoint.get(serverPlayer)) {
                TCRAdvancementData.finishAdvancement(TCRCoreMod.MOD_ID, serverPlayer);
                CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "gamerule keepInventory true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "gamerule mobGriefing false");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " epicskills:battleborn tcrcore:step true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " epicskills:battleborn efn:efn_dodge true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " epicskills:battleborn epicfight:parrying true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " dodge_parry_reward:passive dodge_parry_reward:stamina1 true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add " + player.getGameProfile().getName() + " dodge tcrcore:step");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add " + player.getGameProfile().getName() + " guard epicfight:parrying");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add " + player.getGameProfile().getName() + " passive1 dodge_parry_reward:stamina1");
                ItemUtil.addItem(serverPlayer, AquamiraeItems.FIN_CUTTER.get(), 1);
                ItemUtil.addItem(serverPlayer, Items.OAK_BOAT, 1);
                ItemUtil.addItem(serverPlayer, ModItems.BACKPACK.get(), 1);
                ItemStack trident = Items.TRIDENT.getDefaultInstance();
                trident.enchant(Enchantments.LOYALTY, 3);
                ItemUtil.addItem(serverPlayer, trident);
                ItemStack fishingRod = Items.FISHING_ROD.getDefaultInstance();
                fishingRod.enchant(Enchantments.FISHING_LUCK, 3);
                fishingRod.enchant(Enchantments.FISHING_SPEED, 3);
                ItemUtil.addItem(serverPlayer, fishingRod);
                PlayerDataManager.firstJoint.put(serverPlayer, true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if(player instanceof ServerPlayer serverPlayer) {
            TCRCapabilityProvider.syncPlayerDataToClient(serverPlayer);
        }
    }

}

package com.p1nero.tcrcore.events;


import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.datagen.TCRAdvancementData;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.blay09.mods.waystones.block.ModBlocks;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.sonmok14.fromtheshadows.server.utils.registry.EntityRegistry;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class PlayerEventListeners {

    /**
     * 提示加点
     */
    @SubscribeEvent
    public static void onPlayerLevelUp(PlayerXpEvent.LevelChange event) {
        if(event.getEntity().experienceLevel % 2 == 1 && event.getLevels() > 0) {
            event.getEntity().displayClientMessage(TCRCoreMod.getInfo("press_to_skill_tree"), false);
        }
    }

    @SubscribeEvent
    public static void onPlayerAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if(event.getEntity() instanceof ServerPlayer serverPlayer) {
            String path = event.getAdvancement().getId().getPath();
            String namespace = event.getAdvancement().getId().getNamespace();
            if(namespace.equals(TCRCoreMod.MOD_ID)) {
                if(path.equals("kill_pillager")) {
                    PlayerDataManager.pillagerKilled.put(serverPlayer, true);
                    ItemUtil.addItemEntity(serverPlayer, TCRItems.ANCIENT_ORACLE_FRAGMENT.get().getDefaultInstance());
                }
//                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("press_to_show_progress"), false);
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
//                TCRAdvancementData.finishAdvancement(TCRCoreMod.MOD_ID + "_weapon", serverPlayer);
//                TCRAdvancementData.finishAdvancement(TCRCoreMod.MOD_ID + "_ingredient", serverPlayer);
                CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "gamerule keepInventory true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "gamerule mobGriefing false");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " epicskills:battleborn efn:efn_step true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " epicskills:battleborn efn:efn_dodge true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " epicskills:battleborn epicfight:parrying true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock " + player.getGameProfile().getName() + " dodge_parry_reward:passive dodge_parry_reward:stamina1 true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add " + player.getGameProfile().getName() + " dodge efn:efn_step");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add " + player.getGameProfile().getName() + " guard epicfight:parrying");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add " + player.getGameProfile().getName() + " passive1 dodge_parry_reward:stamina1");
                ItemUtil.addItem(serverPlayer, Items.IRON_SWORD, 1);
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
    public static void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event) {
        //第一次交互给传送石和提示
        if (event.getLevel().getBlockState(event.getPos()).is(ModBlocks.waystone)) {
            if(event.getEntity() instanceof ServerPlayer serverPlayer) {
                if(!PlayerDataManager.wayStoneInteracted.get(serverPlayer)) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("press_to_open_portal_screen"), true);
                    ItemUtil.addItem(serverPlayer, net.blay09.mods.waystones.item.ModItems.warpStone, 1);
                    PlayerDataManager.wayStoneInteracted.put(serverPlayer, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if(event.player.isLocalPlayer() && WorldUtil.inMainLand(event.player)) {
                if(isNearBarrier(event.player)) {
                    event.player.displayClientMessage(TCRCoreMod.getInfo("hit_barrier"), true);
                }
            }
            if(event.player.level() instanceof ServerLevel serverLevel && !PlayerDataManager.bllSummoned.get(event.player) && WorldUtil.isInStructure(event.player, WorldUtil.COVES)) {
                EntityRegistry.BULLDROGIOTH.get().spawn(serverLevel, event.player.getOnPos().above(5), MobSpawnType.SPAWNER);
                PlayerDataManager.bllSummoned.put(event.player, true);
            }
        }

    }

    public static boolean isNearBarrier(Player player) {
        if (player.noPhysics) {
            return false;
        } else {
            float f = player.getDimensions(player.getPose()).width * 1.5F;
            AABB aabb = AABB.ofSize(player.getEyePosition(), f, 1.0E-6D, f);
            return BlockPos.betweenClosedStream(aabb).anyMatch((p_201942_) -> {
                BlockState blockstate = player.level().getBlockState(p_201942_);
                return blockstate.is(Blocks.BARRIER);
            });
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

package com.p1nero.tcrcore.events;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.p1nero.dialog_lib.events.ServerNpcEntityInteractEvent;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.client.sound.CorneliaMusicPlayer;
import com.p1nero.tcrcore.utils.ItemUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class LivingEntityEventListeners {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event){
        LivingEntity living = event.getEntity();
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event){
        LivingEntity living = event.getEntity();
        if(event.getSource().getEntity() instanceof Player player) {
            if(living instanceof CaptainCornelia && !PlayerDataManager.cursedEyeTraded.get(player)) {
                ItemUtil.addItem(player, ModItems.CURSED_EYE.get(), 1, true);
                PlayerDataManager.cursedEyeTraded.put(player, true);
            }
            if(living instanceof ElderGuardian&& !PlayerDataManager.desertEyeTraded.get(player)) {
                ItemUtil.addItem(player, ModItems.DESERT_EYE.get(), 1, true);
                PlayerDataManager.desertEyeTraded.put(player, true);
            }
        }
        if(living instanceof CaptainCornelia) {
            if(living.level().isClientSide) {
                CorneliaMusicPlayer.stopBossMusic(living);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDialog(ServerNpcEntityInteractEvent event) {
        if(event.getSelf() instanceof Villager) {
            TCRCapabilityProvider.getTCRPlayer(event.getServerPlayer()).setCurrentTalkingEntity(null);
        }
    }

    /**
     * 减少呼吸消耗
     */
    @SubscribeEvent
    public static void onLivingBreath(LivingBreatheEvent event){
        LivingEntity living = event.getEntity();
        if(living instanceof Player player) {
            if(player.tickCount % 2 == 0) {
                event.setConsumeAirAmount(0);
            }
        }
    }

    /**
     * 出生地防刷怪
     */
    @SubscribeEvent
    public static void onLivingSpawn(MobSpawnEvent.PositionCheck event){
        if(event.getEntity().position().horizontalDistance() < 300 && event.getEntity() instanceof Enemy) {
            event.setResult(Event.Result.DENY);
        }
    }
}

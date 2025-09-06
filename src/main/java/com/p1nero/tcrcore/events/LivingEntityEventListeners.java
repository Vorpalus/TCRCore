package com.p1nero.tcrcore.events;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla.Scylla_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.items.The_Incinerator;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.p1nero.dialog_lib.events.ServerNpcEntityInteractEvent;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.client.sound.CorneliaMusicPlayer;
import com.p1nero.tcrcore.gameassets.TCRSkills;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.kenddie.fantasyarmor.item.FAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sonmok14.fromtheshadows.server.entity.mob.BulldrogiothEntity;
import net.unusual.blockfactorysbosses.entity.InfernalDragonEntity;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class LivingEntityEventListeners {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event){

    }

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if(!entity.level().isClientSide) {

            if (entity instanceof Scylla_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.CERAUNUS.get(), 1, 0xfff66d);
                event.setCanceled(true);
            }

            if (entity instanceof Ignis_Entity) {
                event.getDrops().clear();
                event.getDrops().add(ItemUtil.addItemEntity(entity, ModItems.THE_INCINERATOR.get(), 1, 0xfff66d));
            }
            if (entity instanceof The_Leviathan_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.TIDAL_CLAWS.get(), 1, 0xfff66d);
            }
            if (entity instanceof Maledictus_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.SOUL_RENDER.get(), 1, 0xfff66d);
                event.setCanceled(true);
            }
            if (entity instanceof Ancient_Remnant_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.WRATH_OF_THE_DESERT.get(), 1, 0xfff66d);
            }
            if(entity instanceof Pillager) {
                if(entity.getRandom().nextFloat() < 0.2F) {
                    ItemUtil.addItemEntity(entity, Items.GOLD_INGOT, 1, 0xc000ff);
                }
                if(entity.getRandom().nextFloat() < 0.1F) {
                    ItemUtil.addItemEntity(entity, Items.DIAMOND, 1, 0xc000ff);
                }
                if(entity.getRandom().nextFloat() < 0.05F) {
                    ItemUtil.addItemEntity(entity, Items.NETHERITE_INGOT, 1, 0xc000ff);
                }
            } else if(entity instanceof Enemy) {
                if(entity.getRandom().nextFloat() < 0.1F) {
                    ItemUtil.addItemEntity(entity, Items.IRON_INGOT, 1, 0xc000ff);
                }
                if(entity.getRandom().nextFloat() < 0.03F) {
                    ItemUtil.addItemEntity(entity, Items.AMETHYST_SHARD, 1, 0xc000ff);
                } else if(entity.getRandom().nextFloat() < 0.01F) {
                    ItemUtil.addItemEntity(entity, FAItems.MOON_CRYSTAL.get(), 1, 0xc000ff);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event){
        LivingEntity livingEntity = event.getEntity();
        if(event.getSource().getEntity() instanceof Player player) {

            if(livingEntity instanceof IronGolem && !PlayerDataManager.stormEyeTraded.get(player) && WorldUtil.isInStructure(livingEntity, "trek:overworld/very_rare/floating_farm_large")) {
                ItemUtil.addItem(player, ModItems.STORM_EYE.get(), 1, true);
                ItemUtil.addItem(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1, true);
                PlayerDataManager.stormEyeTraded.put(player, true);
            }

            if(livingEntity instanceof BulldrogiothEntity && !PlayerDataManager.abyssEyeTraded.get(player) && WorldUtil.isInStructure(livingEntity, WorldUtil.COVES)) {
                ItemUtil.addItem(player, ModItems.ABYSS_EYE.get(), 1, true);
                ItemUtil.addItem(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1, true);
                PlayerDataManager.abyssEyeTraded.put(player, true);
            }

            if(livingEntity instanceof InfernalDragonEntity && !PlayerDataManager.abyssEyeTraded.get(player)) {
                ItemUtil.addItem(player, ModItems.FLAME_EYE.get(), 1, true);
                ItemUtil.addItem(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1, true);
                PlayerDataManager.flameEyeTraded.put(player, true);
            }

            if(livingEntity instanceof CaptainCornelia && !PlayerDataManager.cursedEyeTraded.get(player)) {
                ItemUtil.addItem(player, ModItems.CURSED_EYE.get(), 1, true);
                ItemUtil.addItem(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1, true);
                PlayerDataManager.cursedEyeTraded.put(player, true);
            }
            if(livingEntity instanceof ElderGuardian && !PlayerDataManager.desertEyeTraded.get(player)) {
                ItemUtil.addItem(player, ModItems.DESERT_EYE.get(), 1, true);
                ItemUtil.addItem(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1, true);
                PlayerDataManager.desertEyeTraded.put(player, true);
            }
        }
        if(livingEntity instanceof CaptainCornelia) {
            if(livingEntity.level().isClientSide) {
                CorneliaMusicPlayer.stopBossMusic(livingEntity);
            }
        }
        if(livingEntity instanceof Player player) {
            player.displayClientMessage(TCRCoreMod.getInfo("death_info"), false);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntity() instanceof ServerPlayer serverPlayer) {
            EpicFightCapabilities.getUnparameterizedEntityPatch(serverPlayer, ServerPlayerPatch.class).ifPresent(serverPlayerPatch -> {
                AnimationPlayer player = serverPlayerPatch.getAnimator().getPlayerFor(null);
                //激流期间无敌
                if(player != null && player.getAnimation() == Animations.TSUNAMI_REINFORCED) {
                    event.setAmount(0);
                    event.setCanceled(true);
                }
            });
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
            if(EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).getSkillCapability().hasLearned(TCRSkills.WATER_AVOID)) {
                event.setCanBreathe(true);
            }
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
        if(WorldUtil.inMainLand(event.getEntity()) && event.getEntity() instanceof Enemy) {
            event.setResult(Event.Result.DENY);
        }
    }
}

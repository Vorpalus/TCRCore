package com.p1nero.tcrcore.gameassets;


import com.p1nero.p1nero_ec.PECMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;
import yesman.epicfight.world.capabilities.item.TridentCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = PECMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRWeaponPresets {

    public static final Function<Item, CapabilityItem.Builder> TRIDENT = (item) -> RangedWeaponCapability.builder().zoomInType(CapabilityItem.ZoomInType.USE_TICK)
            .addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_IDLE)
            .addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_WALK)
            .addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_JAVELIN_AIM)
            .addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_JAVELIN_THROW)
            .constructor(TridentCapability::new).collider(ColliderPreset.SPEAR)
            .category(CapabilityItem.WeaponCategories.TRIDENT);


    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(PECMod.MOD_ID, "trident"), TRIDENT);
    }
}

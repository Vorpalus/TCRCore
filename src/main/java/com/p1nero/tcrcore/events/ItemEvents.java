package com.p1nero.tcrcore.events;


import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.ChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class ItemEvents {

    @SubscribeEvent
    public static void onItemDesc(ItemTooltipEvent event) {
        if (List.of(ModItems.ABYSS_EYE.get(), ModItems.STORM_EYE.get(), ModItems.CURSED_EYE.get(), ModItems.MECH_EYE.get(), ModItems.FLAME_EYE.get(), ModItems.DESERT_EYE.get(), ModItems.MONSTROUS_EYE.get(), ModItems.VOID_EYE.get()).contains(event.getItemStack().getItem())) {
            event.getToolTip().add(TCRCoreMod.getInfo("time_to_altar").withStyle(ChatFormatting.GRAY));
        }
    }

}

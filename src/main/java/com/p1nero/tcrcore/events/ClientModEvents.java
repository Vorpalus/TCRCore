package com.p1nero.tcrcore.events;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.block.client.AltarBlockRenderer;
import com.p1nero.tcrcore.block.entity.TCRBlockEntities;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.entity.custom.guider.GuiderRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(TCREntities.CUSTOM_COLOR_ITEM.get(), ItemEntityRenderer::new);
        EntityRenderers.register(TCREntities.GUIDER.get(), GuiderRenderer::new);
    }

    @SubscribeEvent
    public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TCRBlockEntities.ABYSS_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.DESERT_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.STORM_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.FLAME_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.CURSED_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
    }
}

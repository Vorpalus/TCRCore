package com.p1nero.tcrcore.events;

import com.p1nero.dialog_lib.client.screen.DialogueScreen;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.client.gui.CustomGuiRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event){
        if(Minecraft.getInstance().screen instanceof DialogueScreen) {
            event.setCanceled(true);
            return;
        }
        if(event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type()) {
            CustomGuiRenderer.renderSkillPoints(event.getGuiGraphics(), event.getWindow(), event.getPartialTick());
        }
    }

}

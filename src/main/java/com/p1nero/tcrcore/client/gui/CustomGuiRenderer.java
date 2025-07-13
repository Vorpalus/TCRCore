package com.p1nero.tcrcore.client.gui;

import com.mojang.blaze3d.platform.Window;
import com.p1nero.tcrcore.capability.TCRPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

@OnlyIn(Dist.CLIENT)
public class CustomGuiRenderer {

    public static void renderSkillPoints(GuiGraphics guiGraphics, Window window, float partialTick) {
        LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
        if(localPlayerPatch != null) {
            LocalPlayer localPlayer = localPlayerPatch.getOriginal();
            if(!localPlayer.isCreative() && !localPlayer.isSpectator()) {
                Font font = Minecraft.getInstance().font;
                float scale = 0.83333F;
                int width = (int) (114 * scale);
                int height = (int) (13 * scale);
                int x = (int) (window.getGuiScaledWidth() / 2.0F - width * 0.95);
                int y = (int) (window.getGuiScaledHeight() - height * 3.9);
                for(int i = x; i < TCRPlayer.MAX_SKILL_POINTS; i++) {
                    //画底图

                    //画上面
                }

            }
        }
    }
}

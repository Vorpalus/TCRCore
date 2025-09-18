package com.p1nero.tcrcore.client.gui;

import com.p1nero.dialog_lib.api.component.TreeNode;
import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import com.p1nero.dialog_lib.client.screen.ScreenDialogueBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.datagen.lang.TCRLangProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BanPortalScreenHandler {
    public static final String name = "ban_portal";

    @OnlyIn(Dist.CLIENT)
    public static void addScreen() {
        ScreenDialogueBuilder builder = new ScreenDialogueBuilder(TCRCoreMod.MOD_ID, name);

        DialogueScreenBuilder screenBuilder = new DialogueScreenBuilder(null, Component.literal("").append(builder.name().copy().withStyle(ChatFormatting.AQUA)).append(": \n"));

        TreeNode root = new TreeNode(builder.ans(0))
                .addLeaf(builder.opt(0));

        screenBuilder.setAnswerRoot(root);
        Minecraft.getInstance().setScreen(screenBuilder.build());
    }

    public static void onGenerateZH(TCRLangProvider generator) {
        generator.addScreenName(name, "不知何处传来的声音");
        generator.addScreenAns(name, 0, "（传送门并没有如期出现，看来当前还未能达到神之认可。推进主线以提升阶段吧！）");
        generator.addScreenOpt(name, 0, "好吧");
    }

    public static void onGenerateEN(TCRLangProvider generator) {
        generator.addScreenName(name, "A voice from an unknown source");
        generator.addScreenAns(name, 0, "(The portal did not appear as expected. It seems that your game stage is not enough.");
        generator.addScreenOpt(name, 0, "Alright");
    }

}

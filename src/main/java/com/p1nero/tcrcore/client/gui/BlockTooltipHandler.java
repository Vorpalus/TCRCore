package com.p1nero.tcrcore.client.gui;

import com.p1nero.tcrcore.block.custom.AbstractAltarBlock;
import com.p1nero.tcrcore.block.entity.AbstractAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class BlockTooltipHandler {
    
    private static final Map<Block, List<Component>> BLOCK_TOOLTIPS = new ConcurrentHashMap<>();
    private static final int TOOLTIP_MARGIN = 5;
    private static final int LINE_SPACING = 2;
    private static final int BACKGROUND_COLOR = 0xCC000000;
    private static final int BORDER_COLOR = 0xFFFFFFFF;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    
    /**
     * 注册方块的工具提示
     * @param block 方块RegistryObject
     * @param tooltips 工具提示文本列表
     */
    public static void registerBlockTooltip(RegistryObject<Block> block, List<Component> tooltips) {
        if (block != null && tooltips != null && !tooltips.isEmpty()) {
            BLOCK_TOOLTIPS.put(block.get(), new ArrayList<>(tooltips));
        }
    }
    
    /**
     * 注册方块的工具提示（可变参数版本）
     */
    public static void registerBlockTooltip(RegistryObject<Block> block, Component... tooltips) {
        if (tooltips != null && tooltips.length > 0) {
            registerBlockTooltip(block, Arrays.asList(tooltips));
        }
    }
    
    /**
     * 移除方块的工具提示
     */
    public static void removeBlockTooltip(RegistryObject<Block> block) {
        if (block != null) {
            BLOCK_TOOLTIPS.remove(block.get());
        }
    }
    
    /**
     * 清除所有工具提示
     */
    public static void clearAllTooltips() {
        BLOCK_TOOLTIPS.clear();
    }
    
    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        
        // 检查是否在游戏中且没有打开GUI
        if (minecraft.player == null || minecraft.screen != null) {
            return;
        }
        
        // 获取鼠标指向的方块
        HitResult hitResult = minecraft.hitResult;
        if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }
        
        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
        BlockPos blockPos = blockHitResult.getBlockPos();
        Block block = minecraft.level.getBlockState(blockPos).getBlock();
        
        // 检查是否有该方块的工具提示
        List<Component> tooltips = BLOCK_TOOLTIPS.get(block);
        if (tooltips == null || tooltips.isEmpty()) {
            return;
        }

        if(block instanceof AbstractAltarBlock abstractAltarBlock) {
            if(minecraft.level.getBlockEntity(blockPos) instanceof AbstractAltarBlockEntity entity) {
                if(!entity.isActivated()){
                    return;
                }
            }
        }
        
        // 渲染工具提示
        renderTooltip(event.getGuiGraphics(), tooltips, minecraft);
    }
    
    private static void renderTooltip(GuiGraphics guiGraphics, List<Component> tooltips, Minecraft minecraft) {
        Font font = minecraft.font;

        // 计算工具提示尺寸
        int maxWidth = 0;
        int totalHeight = 0;
        
        for (Component line : tooltips) {
            int lineWidth = font.width(line);
            maxWidth = Math.max(maxWidth, lineWidth);
            totalHeight += font.lineHeight + LINE_SPACING;
        }
        
        totalHeight -= LINE_SPACING; // 最后一行不需要间距
        
        int tooltipWidth = maxWidth + TOOLTIP_MARGIN * 2;
        int tooltipHeight = totalHeight + TOOLTIP_MARGIN * 2;
        
        // 计算位置（避免超出屏幕）
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        
        int posX = screenWidth / 2 + 20;
        int posY = screenHeight / 2;
        
        // 绘制背景和边框
        guiGraphics.fill(posX, posY, posX + tooltipWidth, posY + tooltipHeight, BACKGROUND_COLOR);
        guiGraphics.renderOutline(posX, posY, tooltipWidth, tooltipHeight, BORDER_COLOR);
        
        // 绘制文本
        int textY = posY + TOOLTIP_MARGIN;
        for (Component line : tooltips) {
            int textX = posX + TOOLTIP_MARGIN;
            guiGraphics.drawString(font, line, textX, textY, TEXT_COLOR, false);
            textY += font.lineHeight + LINE_SPACING;
        }
    }
}
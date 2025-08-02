package com.p1nero.tcrcore.block.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.p1nero.tcrcore.block.entity.AbstractAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AltarBlockRenderer <T extends AbstractAltarBlockEntity> implements BlockEntityRenderer<T> {
    private ItemStack stack = null;
    public AltarBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull AbstractAltarBlockEntity altarBlockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if(altarBlockEntity.isActivated()) {
            if(stack == null) {
                stack = altarBlockEntity.getItemInnate().getDefaultInstance();
            }
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.35F, 0.5F);
            float time = (altarBlockEntity.getLevel().getGameTime() + partialTick) * 0.05F;
            float floatOffset = (float) Math.sin(time * 2.0F) * 0.1F;
            poseStack.translate(0.0F, floatOffset, 0.0F);
            poseStack.mulPose(Axis.YP.rotation(time));
            float scale = 1.4F + (float) Math.sin(time * 3.0F) * 0.1F;
            poseStack.scale(scale, scale, scale);
            BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(stack);
            Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GROUND,false,poseStack, bufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, model);
            poseStack.popPose();

            //TODO 玩家击败boss则画信标
        }
    }

    @Override
    public boolean shouldRender(@NotNull T blockEntity, @NotNull Vec3 pos) {
        return BlockEntityRenderer.super.shouldRender(blockEntity, pos);
    }
}

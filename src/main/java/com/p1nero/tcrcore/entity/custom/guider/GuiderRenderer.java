package com.p1nero.tcrcore.entity.custom.guider;

import com.mojang.blaze3d.vertex.PoseStack;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GuiderRenderer extends MobRenderer<GuiderEntity, VillagerModel<GuiderEntity>> {
   private static final ResourceLocation SKIN = ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "textures/entity/god.png");

   public GuiderRenderer(EntityRendererProvider.Context context) {
      super(context, new VillagerModel<>(context.bakeLayer(ModelLayers.VILLAGER)), 0.5F);
      this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
      this.addLayer(new CrossedArmsItemLayer<>(this, context.getItemInHandRenderer()));
   }

   @Override
   public void render(@NotNull GuiderEntity guider, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int p_115460_) {
      poseStack.pushPose();
      float floatingHeight = calculateFloatingHeight(guider, partialTicks);
      poseStack.translate(0, floatingHeight + 0.5, 0);
      super.render(guider, entityYaw, partialTicks, poseStack, multiBufferSource, p_115460_);
      poseStack.popPose();
   }

   private float calculateFloatingHeight(GuiderEntity entity, float partialTicks) {
      final float BASE_HEIGHT = 0.1f;
      final float SPEED = 0.1f;
      final float BOBBING_SCALE = 0.5f;
      float time = (entity.level().getGameTime() + partialTicks) * SPEED;
      float entityPhase = entity.getId() * 0.1f;
      return Mth.cos(time + entityPhase) * BASE_HEIGHT * BOBBING_SCALE;
   }

   public @NotNull ResourceLocation getTextureLocation(@NotNull GuiderEntity guider) {
      return SKIN;
   }
}
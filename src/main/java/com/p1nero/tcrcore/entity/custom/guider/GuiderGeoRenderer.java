package com.p1nero.tcrcore.entity.custom.guider;

import com.mojang.blaze3d.vertex.PoseStack;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class GuiderGeoRenderer extends GeoEntityRenderer<GuiderEntity> {
    private final GuiderRenderer renderer;
    public GuiderGeoRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "guider")) {
            @Override
            public void setCustomAnimations(GuiderEntity animatable, long instanceId, AnimationState<GuiderEntity> animationState) {
                CoreGeoBone head = getAnimationProcessor().getBone("Head");

                if (head != null) {
                    EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

                    head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
                    head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
                }
            }
        });
        this.renderer = new GuiderRenderer(context);
    }

    @Override
    public void render(@NotNull GuiderEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if(PlayerDataManager.pillagerKilled.get(localPlayer)) {
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        } else {
            this.renderer.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        }
    }
}

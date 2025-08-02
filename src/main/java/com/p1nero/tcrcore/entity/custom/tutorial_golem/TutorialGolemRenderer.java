package com.p1nero.tcrcore.entity.custom.tutorial_golem;

import com.merlin204.avalon.entity.client.renderer.EmptyRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class TutorialGolemRenderer extends EmptyRenderer {
    private static final ResourceLocation GOLEM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/iron_golem/iron_golem.png");
    public TutorialGolemRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Entity entity) {
        return GOLEM_LOCATION;
    }
}

package com.github.x3rmination.common.entities.SkeletonGladiator;

import com.github.x3rmination.X3DUNGEONS;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonGladiatorRenderer extends BipedRenderer<SkeletonGladiatorEntity, SkeletonGladiatorModel<SkeletonGladiatorEntity>> {

    private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/entity/skeleton_gladiator.png");

    public SkeletonGladiatorRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new SkeletonGladiatorModel(), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new SkeletonGladiatorModel(0.5F), new SkeletonGladiatorModel(1.0F)));
    }

    public ResourceLocation getTextureLocation(SkeletonGladiatorEntity entity) {
        return SKELETON_LOCATION;
    }

}

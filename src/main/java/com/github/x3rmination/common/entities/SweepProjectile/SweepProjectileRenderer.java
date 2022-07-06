package com.github.x3rmination.common.entities.SweepProjectile;

import com.github.x3rmination.X3DUNGEONS;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import javax.annotation.Nullable;

public class SweepProjectileRenderer extends GeoProjectilesRenderer<SweepProjectileEntity> {

    public SweepProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SweepProjectileModel());
    }

    @Override
    public RenderType getRenderType(SweepProjectileEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucentCull(new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/entity/sweep_projectile.png"));
    }
}

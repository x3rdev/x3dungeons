package com.github.x3rmination.common.entities.Floppa;

import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

import javax.annotation.Nullable;

public class FloppaRenderer extends ExtendedGeoEntityRenderer<FloppaEntity> {

    public FloppaRenderer(EntityRendererManager renderManager) {
        super(renderManager, new FloppaModel());
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, FloppaEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, FloppaEntity currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, FloppaEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, FloppaEntity currentEntity, IBone bone) {

    }

    @Override
    protected void preRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, FloppaEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, FloppaEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, FloppaEntity currentEntity) {

    }

}

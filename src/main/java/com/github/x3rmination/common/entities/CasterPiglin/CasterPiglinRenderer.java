package com.github.x3rmination.common.entities.CasterPiglin;

import com.github.x3rmination.common.entities.ModRenderTypes;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

import javax.annotation.Nullable;

public class CasterPiglinRenderer extends ExtendedGeoEntityRenderer<CasterPiglinEntity> {

    public CasterPiglinRenderer(EntityRendererManager renderManager) {
        this(renderManager, new CasterPiglinModel());
    }

    public CasterPiglinRenderer(EntityRendererManager renderManager, CasterPiglinModel modelProvider) {
        super(renderManager, modelProvider);
        this.addLayer(new LayerGlowingAreasGeo<>(this, getGeoModelProvider()::getTextureLocation, getGeoModelProvider()::getModelLocation, ModRenderTypes::emissive));
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, CasterPiglinEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, CasterPiglinEntity currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, CasterPiglinEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, CasterPiglinEntity currentEntity, IBone bone) {

    }

    @Override
    protected void preRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, CasterPiglinEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, CasterPiglinEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, CasterPiglinEntity currentEntity) {

    }


}

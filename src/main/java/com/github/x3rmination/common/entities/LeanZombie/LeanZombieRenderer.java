package com.github.x3rmination.common.entities.LeanZombie;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.example.client.DefaultBipedBoneIdents;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

import javax.annotation.Nullable;

public class LeanZombieRenderer extends ExtendedGeoEntityRenderer<LeanZombieEntity> {

    public LeanZombieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new LeanZombieModel());
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, LeanZombieEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, LeanZombieEntity currentEntity) {
        switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT:
                return currentEntity.isLeftHanded() ? mainHand : offHand;
            case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT:
                return currentEntity.isLeftHanded() ? offHand : mainHand;
            default:
                return null;
        }
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT:
            case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT:
                return ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
            default:
                return ItemCameraTransforms.TransformType.NONE;
        }
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, LeanZombieEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, LeanZombieEntity currentEntity, IBone bone) {

    }

    @Override
    protected void preRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, LeanZombieEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, LeanZombieEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, LeanZombieEntity currentEntity) {

    }
}

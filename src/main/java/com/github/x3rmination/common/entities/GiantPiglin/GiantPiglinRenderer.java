package com.github.x3rmination.common.entities.GiantPiglin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.example.client.DefaultBipedBoneIdents;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

import javax.annotation.Nullable;

public class GiantPiglinRenderer extends ExtendedGeoEntityRenderer<GiantPiglinEntity> {

    public GiantPiglinRenderer(EntityRendererManager renderManager) {
        this(renderManager, new GiantPiglinModel());
    }

    public GiantPiglinRenderer(EntityRendererManager renderManager, GiantPiglinModel modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, GiantPiglinEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, GiantPiglinEntity currentEntity) {
        switch (boneName) {
            case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT:
                return currentEntity.isLeftHanded() ? mainHand : offHand;
            case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT:
                return currentEntity.isLeftHanded() ? offHand : mainHand;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    protected EquipmentSlotType getEquipmentSlotForArmorBone(String boneName, GiantPiglinEntity currentEntity) {
        switch (boneName) {
            case "armorBipedLeftFoot":
            case "armorBipedRightFoot":
                return EquipmentSlotType.FEET;
            case "armorBipedLeftLeg":
            case "armorBipedRightLeg":
                return EquipmentSlotType.LEGS;
            case "armorBipedRightArm":
                return !currentEntity.isLeftHanded() ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND;
            case "armorBipedLeftArm":
                return currentEntity.isLeftHanded() ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND;
            case "armorBipedBody":
                return EquipmentSlotType.CHEST;
            case "armorBipedHead":
                return EquipmentSlotType.HEAD;
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
    protected BlockState getHeldBlockForBone(String boneName, GiantPiglinEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, GiantPiglinEntity currentEntity, IBone bone) {
        if (item == this.mainHand || item == this.offHand) {
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
//            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            matrixStack.scale(2, 2, 2);
            boolean shieldFlag = item.isShield(currentEntity) || item.getItem() instanceof ShieldItem;
            if (item == this.mainHand) {
                if (shieldFlag) {
                    matrixStack.translate(0.0, 0.125, -0.25);
                }
            } else if (shieldFlag) {
                matrixStack.translate(0.0, 0.125, 0);
                matrixStack.scale(-1, 0, 0);
            }
        }
    }

    @Override
    protected void preRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, GiantPiglinEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, GiantPiglinEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, GiantPiglinEntity currentEntity) {

    }
}

package com.github.x3rmination.common.entities.GladiatorSkeleton;

import com.github.x3rmination.X3DUNGEONS;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.example.client.DefaultBipedBoneIdents;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;
import software.bernie.geckolib3.resource.GeckoLibCache;

import javax.annotation.Nullable;

public class GladiatorSkeletonRenderer extends ExtendedGeoEntityRenderer<GladiatorSkeletonEntity> {

    public GladiatorSkeletonRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new GladiatorSkeletonModel());
//        GeckoLibCache.getInstance().parser.register(new Variable("gladiator.xRot", 0));

    }

    @Override
    public RenderType getRenderType(GladiatorSkeletonEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/entity/skeleton_gladiator.png"));
    }

    @Override
    public void render(GladiatorSkeletonEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        GeckoLibCache.getInstance().parser.setValue("x_rotation", entity.getViewXRot(1));
        GeckoLibCache.getInstance().parser.setValue("y_rotation", entity.yHeadRot - entity.yRot);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    protected ItemStack getArmorForBone(String boneName, GladiatorSkeletonEntity currentEntity) {
        switch (boneName) {
            case "armorBipedLeftFoot":
            case "armorBipedRightFoot":
                return boots;
            case "armorBipedLeftLeg":
            case "armorBipedRightLeg":
                return leggings;
            case "armorBipedBody":
            case "armorBipedRightArm":
            case "armorBipedLeftArm":
                return chestplate;
            case "armorBipedHead":
                return helmet;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    protected EquipmentSlotType getEquipmentSlotForArmorBone(String boneName, GladiatorSkeletonEntity currentEntity) {
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

    @Nullable
    @Override
    protected ModelRenderer getArmorPartForBone(String name, BipedModel<?> armorModel) {
        switch (name) {
            case "armorBipedLeftFoot":
            case "armorBipedLeftLeg":
                return armorModel.leftLeg;
            case "armorBipedRightFoot":
            case "armorBipedRightLeg":
                return armorModel.rightLeg;
            case "armorBipedRightArm":
                return armorModel.rightArm;
            case "armorBipedLeftArm":
                return armorModel.leftArm;
            case "armorBipedBody":
                return armorModel.body;
            case "armorBipedHead":
                return armorModel.head;
            default:
                return null;
        }
    }



    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return bone.getName().startsWith("armor");
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, GladiatorSkeletonEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, GladiatorSkeletonEntity currentEntity) {
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
    protected BlockState getHeldBlockForBone(String boneName, GladiatorSkeletonEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, GladiatorSkeletonEntity currentEntity, IBone bone) {

        if (item == this.mainHand || item == this.offHand) {
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            boolean shieldFlag = item.isShield(currentEntity) || item.getItem() instanceof ShieldItem;
            if (item == this.mainHand) {
                if (shieldFlag) {
                    matrixStack.translate(0.0, 0.125, -0.25);
                }
            } else if (shieldFlag) {
                matrixStack.translate(0.0, 0.125, 0);
            }
        }
    }

    @Override
    protected void preRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, GladiatorSkeletonEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(MatrixStack matrixStack, ItemStack item, String boneName, GladiatorSkeletonEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(MatrixStack matrixStack, BlockState block, String boneName, GladiatorSkeletonEntity currentEntity) {

    }
}

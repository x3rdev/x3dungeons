package com.github.x3rmination.common.entities.SkeletonGladiator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.CreatureEntity;

public class SkeletonGladiatorModel<T extends CreatureEntity> extends BipedModel<T> {

    private final ModelRenderer rightBicep;
    private final ModelRenderer rightForearm;
    private final ModelRenderer leftBicep;
    private final ModelRenderer leftForearm;

    public SkeletonGladiatorModel() {
        this(0.0F);
    }

    public SkeletonGladiatorModel(float p_i46303_1_) {
        super(p_i46303_1_);
        texWidth = 64;
        texHeight = 64;


        rightArm = new ModelRenderer(this);
        rightArm.setPos(5.0F, 0.0F, 0.0F);


        rightBicep = new ModelRenderer(this);
        rightBicep.setPos(-5.0F, 24.0F, 0.0F);
        rightArm.addChild(rightBicep);
        rightBicep.texOffs(0, 0).addBox(4.0F, -20.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        rightForearm = new ModelRenderer(this);
        rightForearm.setPos(-5.0F, 24.0F, 0.0F);
        rightArm.addChild(rightForearm);
        rightForearm.texOffs(32, 32).addBox(4.0F, -26.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setPos(-5.0F, 0.0F, 0.0F);


        leftBicep = new ModelRenderer(this);
        leftBicep.setPos(5.0F, 24.0F, 0.0F);
        leftArm.addChild(leftBicep);
        leftBicep.texOffs(24, 0).addBox(-6.0F, -20.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        leftForearm = new ModelRenderer(this);
        leftForearm.setPos(5.0F, 24.0F, 0.0F);
        leftArm.addChild(leftForearm);
        leftForearm.texOffs(34, 14).addBox(-6.0F, -26.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this);
        leftLeg.setPos(-2.0F, 12.0F, 0.0F);
        leftLeg.texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setPos(2.0F, 12.0F, 0.0F);
        rightLeg.texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 0.0F, 0.0F);
        body.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

//    @Override
//    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
////        this.rightArm.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 2.0F * pLimbSwingAmount * 0.5F;
////        this.leftArm.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 2.0F * pLimbSwingAmount * 0.5F;
////        this.rightArm.zRot = 0.0F;
////        this.leftArm.zRot = 0.0F;
////        this.rightLeg.xRot = MathHelper.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
////        this.leftLeg.xRot = MathHelper.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount;
////        this.rightLeg.yRot = 0.0F;
////        this.leftLeg.yRot = 0.0F;
////        this.rightLeg.zRot = 0.0F;
////        this.leftLeg.zRot = 0.0F;
//    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }
}

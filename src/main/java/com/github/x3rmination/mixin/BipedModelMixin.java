package com.github.x3rmination.mixin;

import com.github.x3rmination.common.items.BoneFlute.BoneFluteItem;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedModel.class)
public class BipedModelMixin {

    @Shadow public ModelRenderer leftArm;
    @Shadow public ModelRenderer rightArm;
    @Shadow public ModelRenderer head;

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void setupAnimMixin(LivingEntity livingEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo info) {
        if(livingEntity.getUseItem().getItem() instanceof BoneFluteItem) {
            float floatPi = (float)Math.PI;
            this.leftArm.xRot = this.leftArm.xRot * 0.2F - floatPi/2 + 0.1F;
            this.leftArm.yRot = -floatPi/4 + head.yRot/2;
            this.rightArm.xRot = this.rightArm.xRot * 0.2F - floatPi/2 + 0.1F;
            this.rightArm.yRot = -floatPi/4 + head.yRot/2;
        }
    }
}

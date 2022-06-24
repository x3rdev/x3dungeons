package com.github.x3rmination.common.entities.Spear;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.Spear.SpearEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.IItemTier;
import net.minecraft.util.ResourceLocation;

public class SpearModel <T extends SpearEntity> extends EntityModel<T> {
	public final ResourceLocation textureLocation;
	private final ModelRenderer full;
	private final ModelRenderer tip;
	private final ModelRenderer scarf;
	private final ModelRenderer fullHandle;

	public SpearModel(IItemTier itemTier) {

		this.textureLocation = new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/entity/wood_spear.png");

		texWidth = 32;
		texHeight = 32;
		full = new ModelRenderer(this);
		full.setPos(3.5F, 10.0F, 0.0F);

		tip = new ModelRenderer(this);
		tip.setPos(-10.0F, -10.0F, 0.0F);
		full.addChild(tip);
		tip.texOffs(0, 12).addBox(1.75F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		tip.texOffs(4, 11).addBox(0.75F, -3.25F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);
		tip.texOffs(8, 10).addBox(-0.25F, -3.25F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		tip.texOffs(12, 5).addBox(-1.25F, -4.25F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		tip.texOffs(12, 12).addBox(-2.25F, -4.25F, -0.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		tip.texOffs(0, 14).addBox(-3.25F, -5.25F, -0.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		tip.texOffs(4, 16).addBox(-4.25F, -5.25F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		tip.texOffs(8, 16).addBox(-5.25F, -5.25F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		scarf = new ModelRenderer(this);
		scarf.setPos(-8.25F, -8.25F, -0.5F);
		full.addChild(scarf);
		scarf.texOffs(0, 7).addBox(2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		scarf.texOffs(0, 9).addBox(1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		scarf.texOffs(4, 7).addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		scarf.texOffs(8, 3).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		scarf.texOffs(12, 0).addBox(-2.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);

		fullHandle = new ModelRenderer(this);
		fullHandle.setPos(8.75F, 8.75F, -0.5F);
		full.addChild(fullHandle);
		fullHandle.texOffs(0, 0).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 2).addBox(-3.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 0).addBox(-2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(8, 1).addBox(-5.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 2).addBox(-4.0F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 4).addBox(-4.0F, -4.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(8, 1).addBox(-7.0F, -6.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 2).addBox(-6.0F, -7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 4).addBox(-6.0F, -6.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(8, 1).addBox(-9.0F, -8.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 2).addBox(-8.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 4).addBox(-8.0F, -8.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(8, 1).addBox(-11.0F, -10.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 2).addBox(-10.0F, -11.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 4).addBox(-10.0F, -10.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(8, 1).addBox(-13.0F, -12.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 2).addBox(-12.0F, -13.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 4).addBox(-12.0F, -12.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(8, 1).addBox(-15.0F, -14.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 2).addBox(-14.0F, -15.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 4).addBox(-14.0F, -14.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(8, 1).addBox(-17.0F, -16.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(5, 2).addBox(-16.0F, -17.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		fullHandle.texOffs(0, 4).addBox(-16.0F, -16.0F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);

		full.zRot = (float) (Math.PI/4.1);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		full.xRot = netHeadYaw;
		full.yRot = headPitch;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		full.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}
package com.github.x3rmination.common.entities.renderer;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.SpearEntity;
import com.github.x3rmination.common.entities.model.SpearModel;
import com.github.x3rmination.common.items.SpearItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class SpearRenderer extends EntityRenderer<SpearEntity> {

    public SpearRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(SpearEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        SpearItem item = (SpearItem) (entityIn.getArrowStack().getItem());
        SpearModel spearModel = new SpearModel(item.getTier());
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F));
        IVertexBuilder ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(bufferIn, spearModel.getRenderType(this.getEntityTexture(entityIn)), false, false);
        spearModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }



    @Override
    public ResourceLocation getEntityTexture(SpearEntity entity) {
        return new ResourceLocation(Objects.requireNonNull(entity.getArrowStack().getItem().getCreatorModId(entity.getArrowStack())), "textures/entity/" + entity.getItemTier().toString().toLowerCase() + "_spear.png");
    }
}

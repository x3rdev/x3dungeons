package com.github.x3rmination.common.items.GoldenShield;

import com.github.x3rmination.X3DUNGEONS;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class GoldOverlay<T extends LivingEntity> extends LayerRenderer<T, EntityModel<T>> {

    private static final RenderType TEXTURE = RenderType.entityTranslucent(new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/entity/gold_overlay.png"));

    public GoldOverlay(IEntityRenderer<T, EntityModel<T>> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if(pLivingEntity.getUseItem().getItem() instanceof GoldenShield) {
            IVertexBuilder ivertexbuilder = pBuffer.getBuffer(TEXTURE);
            this.getParentModel().renderToBuffer(pMatrixStack, ivertexbuilder, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.7F);
        }
    }
}

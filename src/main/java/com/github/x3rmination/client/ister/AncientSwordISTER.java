package com.github.x3rmination.client.ister;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.items.AncientSword.AncientSwordItem;
import com.github.x3rmination.common.items.AncientSword.AncientSwordModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AncientSwordISTER extends ItemStackTileEntityRenderer {

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        AncientSwordModel swordModel = new AncientSwordModel();
        if(stack.getItem() instanceof AncientSwordItem) {
            matrix.pushPose();
            IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(buffer, swordModel.renderType(new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/entity/ancient_sword.png")), true, true);
            swordModel.renderToBuffer(matrix, vertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrix.popPose();
        }
    }
}

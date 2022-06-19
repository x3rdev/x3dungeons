package com.github.x3rmination.client.ister;

import com.github.x3rmination.common.entities.model.SpearModel;
import com.github.x3rmination.common.items.SpearItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpearISTER extends ItemStackTileEntityRenderer{

    private final SpearModel spearModel = new SpearModel(ItemTier.WOOD);

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if(stack.getItem() instanceof SpearItem) {
            matrix.push();
            matrix.scale(1, -1, -1);
            IVertexBuilder vertexBuilder = ItemRenderer.getEntityGlintVertexBuilder(buffer, spearModel.getRenderType(SpearModel.TEXTURE_LOCATION), false, true);
            spearModel.render(matrix, vertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrix.pop();
        }
    }


}

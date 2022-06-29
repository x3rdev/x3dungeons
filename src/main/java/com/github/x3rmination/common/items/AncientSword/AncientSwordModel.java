package com.github.x3rmination.common.items.AncientSword;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class AncientSwordModel extends Model {
    private final ModelRenderer sword;

    public AncientSwordModel() {
        super(RenderType::entitySolid);
        texWidth = 32;
        texHeight = 32;

        sword = new ModelRenderer(this);
        sword.setPos(-9.0F, 35.0F, -0.5F);
        sword.texOffs(0, 0).addBox(-2.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        sword.texOffs(4, 0).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        sword.texOffs(8, 0).addBox(0.0F, -4.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        sword.texOffs(12, 0).addBox(1.0F, -5.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        sword.texOffs(0, 2).addBox(1.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 0).addBox(2.0F, -6.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        sword.texOffs(4, 4).addBox(2.0F, -9.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        sword.texOffs(0, 4).addBox(3.0F, -9.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(8, 4).addBox(4.0F, -9.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        sword.texOffs(12, 4).addBox(5.0F, -10.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        sword.texOffs(20, 0).addBox(6.0F, -11.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(7.0F, -12.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(8.0F, -13.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(9.0F, -14.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(10.0F, -15.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(11.0F, -16.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(12.0F, -17.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(13.0F, -18.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(14.0F, -19.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(15.0F, -20.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(16.0F, -21.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(16, 4).addBox(17.0F, -22.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        sword.texOffs(24, 0).addBox(18.0F, -22.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        sword.texOffs(28, 0).addBox(19.0F, -22.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        sword.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }


}

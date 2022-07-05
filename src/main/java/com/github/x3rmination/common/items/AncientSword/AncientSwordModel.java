package com.github.x3rmination.common.items.AncientSword;

import com.github.x3rmination.X3DUNGEONS;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AncientSwordModel extends AnimatedGeoModel<AncientSwordItem> {

    @Override
    public ResourceLocation getModelLocation(AncientSwordItem object) {
        return new ResourceLocation(X3DUNGEONS.MOD_ID, "geo/ancient_sword.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AncientSwordItem object) {
        return new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/item/ancient_sword.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AncientSwordItem animatable) {
        return new ResourceLocation(X3DUNGEONS.MOD_ID, "animations/item/ancient_sword.animation.png");
    }


}

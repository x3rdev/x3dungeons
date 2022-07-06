package com.github.x3rmination.common.entities.SweepProjectile;

import com.github.x3rmination.X3DUNGEONS;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SweepProjectileModel extends AnimatedGeoModel {

    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(X3DUNGEONS.MOD_ID, "geo/sweep_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(X3DUNGEONS.MOD_ID, "textures/entity/sweep_projectile.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object animatable) {
        return new ResourceLocation(X3DUNGEONS.MOD_ID, "animations/entity/sweep_projectile.animation.json");
    }
}

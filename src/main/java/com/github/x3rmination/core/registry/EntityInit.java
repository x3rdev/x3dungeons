package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonEntity;
import com.github.x3rmination.common.entities.GladiatorSkeleton.GladiatorSkeletonEntity;
import com.github.x3rmination.common.entities.Spear.SpearEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, X3DUNGEONS.MOD_ID);

    public static final RegistryObject<EntityType<SpearEntity>> SPEAR = ENTITIES.register("spear",
            () -> EntityType.Builder.<SpearEntity>of(SpearEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "spear").toString()));

    public static final RegistryObject<EntityType<GladiatorSkeletonEntity>> GLADIATOR_SKELETON = ENTITIES.register("gladiator_skeleton",
            () -> EntityType.Builder.of(GladiatorSkeletonEntity::new, EntityClassification.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "gladiator_skeleton").toString()));

    public static final RegistryObject<EntityType<AncientSkeletonEntity>> ANCIENT_SKELETON = ENTITIES.register("ancient_skeleton",
            () -> EntityType.Builder.of(AncientSkeletonEntity::new, EntityClassification.MONSTER)
                    .sized(1.2F, 4F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "ancient_skeleton").toString()));
}

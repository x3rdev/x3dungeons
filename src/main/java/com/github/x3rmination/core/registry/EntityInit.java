package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.SpearEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, X3DUNGEONS.MOD_ID);

    public static final RegistryObject<EntityType<SpearEntity>> SPEAR = ENTITIES.register("spear",
            () -> EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F)
                    .trackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "spear").toString()));
}

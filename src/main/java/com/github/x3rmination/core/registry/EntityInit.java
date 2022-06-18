package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, X3DUNGEONS.MOD_ID);
}

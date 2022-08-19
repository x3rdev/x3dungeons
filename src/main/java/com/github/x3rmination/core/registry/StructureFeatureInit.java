package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class StructureFeatureInit {
    public static StructureFeature<?, ?> CONFIGURED_SWAG_DRAGON = StructureInit.SWAG_DRAGON.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_ZOMBIE_DUNGEON = StructureInit.ZOMBIE_DUNGEON.get().configured(IFeatureConfig.NONE);
    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry, new ResourceLocation(X3DUNGEONS.MOD_ID, "configured_swag_dragon"), CONFIGURED_SWAG_DRAGON);
        Registry.register(registry, new ResourceLocation(X3DUNGEONS.MOD_ID, "configured_zombie_dungeon"), CONFIGURED_ZOMBIE_DUNGEON);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureInit.SWAG_DRAGON.get(), CONFIGURED_SWAG_DRAGON);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(StructureInit.ZOMBIE_DUNGEON.get(), CONFIGURED_ZOMBIE_DUNGEON);
    }
}

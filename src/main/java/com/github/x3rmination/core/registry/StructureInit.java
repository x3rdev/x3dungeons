package com.github.x3rmination.core.registry;

import com.github.x3rmination.core.config.X3DUNGEONSConfig;
import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.structures.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class StructureInit {

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, X3DUNGEONS.MOD_ID);

    public static final RegistryObject<StructureBase<NoFeatureConfig>> SWAG_DRAGON = STRUCTURES.register("swag_dragon", SwagDragonStructure::new);
    public static final RegistryObject<StructureBase<NoFeatureConfig>> ZOMBIE_DUNGEON = STRUCTURES.register("zombie_dungeon", ZombieDungeonStructure::new);
    public static final RegistryObject<StructureBase<NoFeatureConfig>> PIGLIN_CAMP = STRUCTURES.register("piglin_camp", PiglinCampStructure::new);
    public static final RegistryObject<StructureBase<NoFeatureConfig>> PIGLIN_FORGE = STRUCTURES.register("piglin_forge", PiglinForgeStructure::new);


    public static void setupStructures() {
        setupMapSpacingAndLand(SWAG_DRAGON.get(), new StructureSeparationSettings(X3DUNGEONSConfig.swag_dragon_max_seperation.get(), X3DUNGEONSConfig.swag_dragon_min_seperation.get(), 915238743), true);
        setupMapSpacingAndLand(ZOMBIE_DUNGEON.get(), new StructureSeparationSettings(50,20, 1437860516), false);
        setupMapSpacingAndLand(PIGLIN_CAMP.get(), new StructureSeparationSettings(X3DUNGEONSConfig.piglin_camp_max_seperation.get(), X3DUNGEONSConfig.piglin_camp_min_seperation.get(), 383452451), false);
        setupMapSpacingAndLand(PIGLIN_FORGE.get(), new StructureSeparationSettings(X3DUNGEONSConfig.piglin_forge_max_seperation.get(), X3DUNGEONSConfig.piglin_forge_min_seperation.get(), 114452251), true);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
        }

        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

            if(structureMap instanceof ImmutableMap){
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }
}

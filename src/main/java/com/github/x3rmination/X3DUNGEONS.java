package com.github.x3rmination;

import com.github.x3rmination.core.config.X3DUNGEONSConfig;
import com.github.x3rmination.core.event.X3DUNGEONSEvents;
import com.github.x3rmination.core.registry.*;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Mod("x3dungeons")
public class X3DUNGEONS {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "x3dungeons";

    public X3DUNGEONS() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        // Items
        ItemInit.ITEMS.register(modEventBus);
        ItemInit.ARTIFACTS.register(modEventBus);

        // Entities
        EntityInit.ENTITIES.register(modEventBus);

        // Enchantments
        EnchantmentInit.ENCHANTMENTS.register(modEventBus);

        // Structures
        StructureInit.STRUCTURES.register(modEventBus);

        forgeBus.register(this);
        forgeBus.register(new X3DUNGEONSEvents());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, X3DUNGEONSConfig.SPEC, "x3dungeons-common.toml");

        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
    }

    private void setup(final FMLCommonSetupEvent event) {
        StructureFeatureInit.registerConfiguredStructures();
        event.enqueueWork(() -> {
            StructureInit.setupStructures();
            EntitySpawnPlacementRegistry.register(EntityInit.GLADIATOR_SKELETON.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CURIO.getMessageBuilder().size(4).build());
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        /*
         * Add our structure to all biomes including other modded biomes.
         * You can skip or add only to certain biomes based on stuff like biome category,
         * temperature, scale, precipitation, mod id, etc. All kinds of options!
         *
         * You can even use the BiomeDictionary as well! To use BiomeDictionary, do
         * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
         * registrykey. Then that can be fed into the dictionary to get the biome's types.
         */
        if(event.getCategory() == Biome.Category.JUNGLE || event.getCategory() == Biome.Category.ICY) {
            if (event.getScale() < 0.5) {
                event.getGeneration().getStructures().add(() -> StructureFeatureInit.CONFIGURED_SWAG_DRAGON);

            }
        }
        if(event.getScale() < 0.5) {
//            event.getGeneration().getStructures().add(() -> StructureFeatureInit.CONFIGURED_ZOMBIE_DUNGEON);
            event.getGeneration().getStructures().add(() -> StructureFeatureInit.CONFIGURED_PIGLIN_CAMP);
        }
        if(event.getCategory() == Biome.Category.NETHER) {
            event.getGeneration().getStructures().add(() -> StructureFeatureInit.CONFIGURED_PIGLIN_FORGE);
        }

    }

    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                X3DUNGEONS.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(StructureInit.SWAG_DRAGON.get(), DimensionStructuresSettings.DEFAULTS.get(StructureInit.SWAG_DRAGON.get()));
            tempMap.putIfAbsent(StructureInit.ZOMBIE_DUNGEON.get(), DimensionStructuresSettings.DEFAULTS.get(StructureInit.ZOMBIE_DUNGEON.get()));
            tempMap.putIfAbsent(StructureInit.PIGLIN_CAMP.get(), DimensionStructuresSettings.DEFAULTS.get(StructureInit.PIGLIN_CAMP.get()));
            tempMap.putIfAbsent(StructureInit.PIGLIN_FORGE.get(), DimensionStructuresSettings.DEFAULTS.get(StructureInit.PIGLIN_FORGE.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}

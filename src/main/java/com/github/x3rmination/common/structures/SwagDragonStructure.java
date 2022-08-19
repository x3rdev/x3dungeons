package com.github.x3rmination.common.structures;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.core.registry.EntityInit;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class SwagDragonStructure extends StructureBase<NoFeatureConfig> {

    public SwagDragonStructure() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
        return SwagDragonStructure.Start::new;
    }

    private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
            new MobSpawnInfo.Spawners(EntityInit.GLADIATOR_SKELETON.get(), 100, 1, 1)
    );

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
        return STRUCTURE_MONSTERS;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos centerPos = new BlockPos(x, chunkGenerator.getFirstFreeHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG) - 10, z);
            VillageConfig frontConfig = new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(X3DUNGEONS.MOD_ID, "swag_dragon/start_front_pool")), 10);
            VillageConfig middleConfig = new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(X3DUNGEONS.MOD_ID, "swag_dragon/start_middle_pool")), 10);
            VillageConfig backConfig = new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(X3DUNGEONS.MOD_ID, "swag_dragon/start_back_pool")), 10);
            JigsawManager.addPieces(dynamicRegistryManager, frontConfig, AbstractVillagePiece::new, chunkGenerator, templateManagerIn, centerPos, this.pieces, new FakeRandom(), false, false);
            JigsawManager.addPieces(dynamicRegistryManager, middleConfig, AbstractVillagePiece::new, chunkGenerator, templateManagerIn, centerPos, this.pieces, new FakeRandom(), false, false);
            JigsawManager.addPieces(dynamicRegistryManager, backConfig, AbstractVillagePiece::new, chunkGenerator, templateManagerIn, centerPos, this.pieces, new FakeRandom(), false, false);

            this.pieces.forEach(piece -> piece.move(0, 1, 0));
            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for(StructurePiece structurePiece : this.pieces) {
                ResourceLocation resourceLocation = getResourceForPiece(structurePiece);
                switch (resourceLocation.getPath()) {
                    case "swag_dragon_head":
                    case "swag_dragon_neck":
                        structurePiece.move(9, 0, -19);
                        break;
                    case "swag_dragon_rib_back":
                    case "swag_dragon_tail":
                        structurePiece.move(0, 0, 48);
                        break;

                }
                structurePiece.move(xOffset, 0, zOffset);
            }
            this.calculateBoundingBox();
        }

        private ResourceLocation getResourceForPiece(StructurePiece structurePiece) {
            if(structurePiece instanceof AbstractVillagePiece) {
                AbstractVillagePiece abstractVillagePiece = ((AbstractVillagePiece) structurePiece);
                if(abstractVillagePiece.getElement() instanceof SingleJigsawPiece) {
                    SingleJigsawPiece jigsawPiece = ((SingleJigsawPiece) abstractVillagePiece.getElement());
                    Either<ResourceLocation, Template> template = jigsawPiece.template;
                    if (template.left().isPresent()) {
                        return template.left().orElseThrow(NullPointerException::new);
                    }
                }
            }
            return new ResourceLocation(X3DUNGEONS.MOD_ID, "empty");
        }
    }

    public static class FakeRandom extends Random {
        @Override
        public int nextInt(int bound) {
            return 0;
        }
    }
}

package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonEntity;
import com.github.x3rmination.common.entities.CannonPiglin.CannonPiglinEntity;
import com.github.x3rmination.common.entities.Floppa.FloppaEntity;
import com.github.x3rmination.common.entities.GladiatorSkeleton.GladiatorSkeletonEntity;
import com.github.x3rmination.common.entities.LeanZombie.LeanZombieEntity;
import com.github.x3rmination.common.entities.Spear.SpearEntity;
import com.github.x3rmination.common.entities.SweepProjectile.SweepProjectileEntity;
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

    public static final RegistryObject<EntityType<LeanZombieEntity>> LEAN_ZOMBIE = ENTITIES.register("lean_zombie",
            () -> EntityType.Builder.of(LeanZombieEntity::new, EntityClassification.MONSTER)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "lean_zombie").toString()));

    public static final RegistryObject<EntityType<FloppaEntity>> FLOPPA = ENTITIES.register("floppa",
            () -> EntityType.Builder.of(FloppaEntity::new, EntityClassification.CREATURE)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "floppa").toString()));

    public static final RegistryObject<EntityType<AncientSkeletonEntity>> ANCIENT_SKELETON = ENTITIES.register("ancient_skeleton",
            () -> EntityType.Builder.of(AncientSkeletonEntity::new, EntityClassification.MONSTER)
                    .sized(1.2F, 4F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "ancient_skeleton").toString()));

    public static final RegistryObject<EntityType<CannonPiglinEntity>> CANNON_PIGLIN = ENTITIES.register("cannon_piglin",
            () -> EntityType.Builder.of(CannonPiglinEntity::new, EntityClassification.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "cannon_piglin").toString()));

    public static final RegistryObject<EntityType<SweepProjectileEntity>> SWEEP_PROJECTILE = ENTITIES.register("sweep_projectile",
            () -> EntityType.Builder.<SweepProjectileEntity>of(SweepProjectileEntity::new, EntityClassification.MISC)
                    .sized(2F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(X3DUNGEONS.MOD_ID, "sweep_projectile").toString()));

}

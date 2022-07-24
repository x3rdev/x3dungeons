package com.github.x3rmination.common.entities.AncientSkeleton;

import com.github.x3rmination.common.entities.SweepProjectile.SweepProjectileEntity;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class AncientSkeletonEntity extends MonsterEntity implements IAnimatable {

    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private final ServerBossInfo bossEvent = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS)).setDarkenScreen(true);
    private static final DataParameter<Boolean> PERFORM_ATTACK_0_ANIM = EntityDataManager.defineId(AncientSkeletonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PERFORM_ATTACK_1_ANIM = EntityDataManager.defineId(AncientSkeletonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PERFORM_ATTACK_2_ANIM = EntityDataManager.defineId(AncientSkeletonEntity.class, DataSerializers.BOOLEAN);

    public AncientSkeletonEntity(EntityType<? extends AncientSkeletonEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new AncientSkeletonGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        ItemStack chestStack = new ItemStack(Items.DIAMOND_CHESTPLATE);
        chestStack.enchant(Enchantments.PROJECTILE_PROTECTION, 4);
        ItemStack legStack = new ItemStack(Items.DIAMOND_LEGGINGS);
        legStack.enchant(Enchantments.PROJECTILE_PROTECTION, 4);
        ItemStack bootStack = new ItemStack(Items.DIAMOND_BOOTS);
        bootStack.enchant(Enchantments.PROJECTILE_PROTECTION, 4);

        this.setItemSlot(EquipmentSlotType.CHEST, chestStack);
        this.setItemSlot(EquipmentSlotType.LEGS, legStack);
        this.setItemSlot(EquipmentSlotType.FEET, bootStack);
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.ANCIENT_SWORD.get()));
        this.setDropChance(EquipmentSlotType.MAINHAND, 2.0F);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.populateDefaultEquipmentSlots(pDifficulty);
        this.setCanPickUpLoot(false);
        return pSpawnData;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if(pSource.isProjectile()) pAmount *= 0.5F;
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 4, this::predicate));
        data.addAnimationController(new AnimationController(this, "walk_controller", 4, this::walk_predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_0_ANIM))) {
            this.getNavigation().stop();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ancient_skeleton.attack0", false));
            return PlayState.CONTINUE;
        }
        if(Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_1_ANIM))) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ancient_skeleton.attack1", false));
            return PlayState.CONTINUE;
        }
        if(Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_2_ANIM))) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ancient_skeleton.attack2", false));
            return PlayState.CONTINUE;
        }
        if(event.isMoving() && event.getController().getCurrentAnimation() == null) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ancient_skeleton.idle", true));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState walk_predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !isAttacking()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ancient_skeleton.walk", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private boolean isAttacking() {
        if(Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_0_ANIM))) return true;
        if(Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_1_ANIM))) return true;
        return Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_2_ANIM));
    }

    @Override
    protected void customServerAiStep() {
        this.bossEvent.setPercent(this.getHealth() / this.getMaxHealth());
        super.customServerAiStep();
    }

    private void spawnAttack1Particles(ServerWorld serverWorld) {
        for (int i = 1; i < 8; i++) {
            double xModifier = Math.sin(i * Math.PI/4);
            double zModifier = Math.cos(i * Math.PI/4);
            for (int j = 0; j < 4; j++) {
                serverWorld.sendParticles(new BlockParticleData(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getX() + xModifier + j, this.getY() + 0.2, this.getZ() + zModifier + j, 10, 1, 1, 1, 1);
            }
        }
    }

    @Override
    public void animateHurt() {
    }

    @Override
    public void startSeenByPlayer(ServerPlayerEntity pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        this.bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayerEntity pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        this.bossEvent.removePlayer(pServerPlayer);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PERFORM_ATTACK_0_ANIM, false);
        this.entityData.define(PERFORM_ATTACK_1_ANIM, false);
        this.entityData.define(PERFORM_ATTACK_2_ANIM, false);
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.remove();
        } else {
            this.noActionTime = 0;
        }
    }

    public class AncientSkeletonGoal extends Goal {
        private final AncientSkeletonEntity entity;
        private static final int COOLDOWN_ATTACK0 = 70;
        private static final int COOLDOWN_ATTACK1 = 400;
        private static final int COOLDOWN_ATTACK2 = 200;
        private int attack0Progress = 0;
        private int attack1Progress = 0;
        private int attack2Progress = 0;
        private int pathRecalculation = 0;
        private int timeUntilDamage;
        private int nextAttack = -1;



        public AncientSkeletonGoal(AncientSkeletonEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK, Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.entity.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public void start() {
            this.entity.getNavigation().moveTo(this.entity.getTarget(),1);
            this.entity.setAggressive(true);
        }

        @Override
        public void stop() {
            this.entity.setAggressive(false);
            this.entity.getNavigation().stop();
        }

        @Override
        public void tick() {
            LivingEntity target = this.entity.getTarget();
            if(target == null || !target.isAlive()) return;
            double distance = this.entity.distanceTo(target);
            this.entity.getLookControl().setLookAt(target, 30, 30);

            if(pathRecalculation > 0) {
                pathRecalculation--;
            } else {
                this.entity.getNavigation().stop();
                this.entity.getNavigation().moveTo(this.entity.getTarget(), 1);
                pathRecalculation = (int) (this.entity.getRandom().nextGaussian()*50);
            }

            if(attack0Progress < COOLDOWN_ATTACK0) attack0Progress++;
            if(attack1Progress < COOLDOWN_ATTACK1) attack1Progress++;
            if(attack2Progress < COOLDOWN_ATTACK2) attack2Progress++;

            if(timeUntilDamage > 0) timeUntilDamage--;
            if(timeUntilDamage == 0) {
                timeUntilDamage = -1;
                ServerWorld serverWorld = this.entity.level.getServer().getLevel(this.entity.level.dimension());
                if(nextAttack == 0) {
                    if(distance < 4)  {
                        this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundCategory.HOSTILE ,10, 1F);
                        target.hurt(DamageSource.mobAttack(this.entity),  5 + (float) Math.round(this.entity.random.nextGaussian() * 10));
                    } else {
                        this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.PLAYER_ATTACK_NODAMAGE, SoundCategory.HOSTILE ,10, 0.8F);
                    }
                    this.entity.entityData.set(PERFORM_ATTACK_0_ANIM, false);
                }
                if(nextAttack == 1) {
                    this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.GILDED_BLACKSTONE_BREAK, SoundCategory.HOSTILE ,10, 0.8F);
                    spawnAttack1Particles(serverWorld);
                    AxisAlignedBB box = new AxisAlignedBB(this.entity.blockPosition().north(6).east(6).above(2), this.entity.blockPosition().south(6).west(6).below(2));
                    List<Entity> entitiesInArea = this.entity.level.getEntities(this.entity, box);
                    for(Entity areaEntity : entitiesInArea) {
                        areaEntity.hurt(DamageSource.mobAttack(this.entity), 20);
                    }
                    this.entity.entityData.set(PERFORM_ATTACK_1_ANIM, false);
                }
                if(nextAttack == 2) {
                    this.entity.setYBodyRot(this.entity.yHeadRot);
                    SweepProjectileEntity sweepEntity = new SweepProjectileEntity(this.entity, this.entity.level);
                    sweepEntity.shootFromRotation(this.entity, this.entity.xRot, this.entity.yHeadRot, 0, 2, 0);
                    sweepEntity.setNoGravity(true);
                    sweepEntity.setDamage(4);
                    this.entity.level.addFreshEntity(sweepEntity);
                    this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundCategory.HOSTILE ,8, 3F);
                    this.entity.entityData.set(PERFORM_ATTACK_2_ANIM, false);
                }
                nextAttack = -1;
            }
            double rand = Math.random();
            if(!isAttacking()) {
                if (rand > 0.5) {
                    if (distance < 4 && attack0Progress == COOLDOWN_ATTACK0 && nextAttack == -1) {
                        this.entity.entityData.set(PERFORM_ATTACK_0_ANIM, true);
                        timeUntilDamage = 28;
                        attack0Progress = 0;
                        nextAttack = 0;
                        return;
                    }
                } else if (distance < 6 && attack1Progress == COOLDOWN_ATTACK1 && nextAttack == -1) {
                    this.entity.entityData.set(PERFORM_ATTACK_1_ANIM, true);
                    timeUntilDamage = 32;
                    attack1Progress = 0;
                    nextAttack = 1;
                    return;
                } else if (distance > 5 && attack2Progress == COOLDOWN_ATTACK2) {
                    this.entity.entityData.set(PERFORM_ATTACK_2_ANIM, true);
                    timeUntilDamage = 32;
                    attack2Progress = 0;
                    nextAttack = 2;
                }
            }
        }
    }
}

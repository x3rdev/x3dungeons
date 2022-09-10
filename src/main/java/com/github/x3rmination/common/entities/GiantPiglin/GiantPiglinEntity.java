package com.github.x3rmination.common.entities.GiantPiglin;

import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinAction;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GiantPiglinEntity extends AbstractPiglinEntity implements IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    private static final DataParameter<Boolean> PERFORM_ATTACK_0_ANIM = EntityDataManager.defineId(AncientSkeletonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PERFORM_ATTACK_1_ANIM = EntityDataManager.defineId(AncientSkeletonEntity.class, DataSerializers.BOOLEAN);

    public GiantPiglinEntity(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.5D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new GiantPiglinGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, WitherSkeletonEntity.class, true));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 4, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_0_ANIM))) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.giant_piglin.swing0", false));
            return PlayState.CONTINUE;
        }
        if(Boolean.TRUE.equals(this.entityData.get(PERFORM_ATTACK_1_ANIM))) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.giant_piglin.swing1", false));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.giant_piglin.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.giant_piglin.idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.remove();
        } else {
            this.noActionTime = 0;
        }
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.populateDefaultEquipmentSlots(pDifficulty);
        this.populateDefaultEquipmentEnchantments(pDifficulty);
        return pSpawnData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
    }

    @Override
    protected boolean canHunt() {
        return false;
    }

    @Override
    protected boolean isImmuneToZombification() {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.entityData.get(PERFORM_ATTACK_0_ANIM).equals(Boolean.TRUE) || this.entityData.get(PERFORM_ATTACK_1_ANIM).equals(Boolean.TRUE)) {
            this.setDeltaMovement(0,this.getDeltaMovement().y,0);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PERFORM_ATTACK_0_ANIM, false);
        this.entityData.define(PERFORM_ATTACK_1_ANIM, false);
    }

    @Override
    public PiglinAction getArmPose() {
        return null;
    }

    @Override
    protected void playConvertedSound() {

    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(@Nullable LivingEntity pLivingEntity) {
        this.target = pLivingEntity;
    }

    public static class GiantPiglinGoal extends Goal {
        private final GiantPiglinEntity entity;
        private int pathDelay = 0;
        private int cdAttack0 = 0;
        private int cdAttack1 = 0;
        private int executionId = -1;
        private int executionDelay = 0;
        private int nextAttackDelay = 0;
        private double distanceToTarget;

        public GiantPiglinGoal(GiantPiglinEntity entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.entity.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public void start() {
            followTarget();
        }

        @Override
        public void stop() {
            this.entity.getNavigation().recomputePath();
        }

        @Override
        public void tick() {
            LivingEntity target = this.entity.getTarget();
            if(target == null || target.isDeadOrDying() || this.entity.level == null) return;
            distanceToTarget = this.entity.distanceToSqr(target);
            decreaseCoolDowns();
            this.entity.lookControl.setLookAt(target, 30.0F, 30.0F);
            if (executionId == -1 && --nextAttackDelay < 1) {
                queueAttack();
            } else {
                if(--executionDelay < 1) {
                    performAttack();
                }
            }
            if(--pathDelay < 1) {
                followTarget();
                pathDelay = 15;
            }
        }

        private void decreaseCoolDowns() {
            cdAttack0--;
            cdAttack1--;
        }

        private void followTarget() {
            if(this.entity.getTarget() != null && executionId == -1) this.entity.getNavigation().moveTo(this.entity.getTarget(), 1);
        }

        private void queueAttack() {
            if(distanceToTarget < 4 && cdAttack0 < 1) {
                executionDelay = 20;
                executionId = 0;
                this.entity.entityData.set(PERFORM_ATTACK_0_ANIM, true);
                this.entity.getNavigation().stop();
            } else if(distanceToTarget < 4 && cdAttack1 < 1) {
                executionDelay = 20;
                executionId = 1;
                this.entity.entityData.set(PERFORM_ATTACK_1_ANIM, true);
                this.entity.getNavigation().stop();
            } else {
                followTarget();
            }
        }

        private void performAttack() {
            switch (executionId) {
                case 0:
                    if(distanceToTarget < 4)  {
                        this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.PLAYER_BIG_FALL, SoundCategory.HOSTILE ,10, 1F);
                        this.entity.getTarget().hurt(DamageSource.mobAttack(this.entity),  7 + (float) Math.round(this.entity.random.nextGaussian() * 10));
                    } else {
                        this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.GILDED_BLACKSTONE_FALL, SoundCategory.HOSTILE ,10, 1F);
                    }
                    this.entity.entityData.set(PERFORM_ATTACK_0_ANIM, false);
                    cdAttack0 = 21;
                    break;
                case 1:
                    if(distanceToTarget < 4)  {
                        this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.PLAYER_BIG_FALL, SoundCategory.HOSTILE ,10, 1F);
                        this.entity.getTarget().hurt(DamageSource.mobAttack(this.entity),  7 + (float) Math.round(this.entity.random.nextGaussian() * 10));
                    } else {
                        this.entity.level.playSound(null, this.entity.blockPosition(), SoundEvents.GILDED_BLACKSTONE_FALL, SoundCategory.HOSTILE ,10, 1F);
                    }
                    this.entity.entityData.set(PERFORM_ATTACK_1_ANIM, false);
                    cdAttack1 = 21;
                    break;

            }
            executionId = -1;
        }
    }
}

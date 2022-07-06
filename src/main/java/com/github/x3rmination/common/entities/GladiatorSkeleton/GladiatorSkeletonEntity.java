package com.github.x3rmination.common.entities.GladiatorSkeleton;

import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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

public class GladiatorSkeletonEntity extends MonsterEntity implements IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    private static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.defineId(GladiatorSkeletonEntity.class, DataSerializers.BOOLEAN);

    public GladiatorSkeletonEntity(EntityType<? extends GladiatorSkeletonEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WolfEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.9D, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    public void aiStep() {
        boolean flag = this.isSunBurnTick();
        if (flag) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.HEAD);
            if (!itemstack.isEmpty()) {
                if (itemstack.isDamageableItem()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                    if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                        this.broadcastBreakEvent(EquipmentSlotType.HEAD);
                        this.setItemSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                    }
                }

                flag = false;
            }

            if (flag) {
                this.setSecondsOnFire(8);
            }
        }
        super.aiStep();
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        if(pDifficulty.getDifficulty() == Difficulty.HARD && Math.random() > 0.7) {
            this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
            this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
            this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(Items.DIAMOND_BOOTS));
            this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.DIAMOND_SPEAR.get()));
        } else {
            this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
            this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
            this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));
            this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(Items.IRON_BOOTS));
            this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.IRON_SPEAR.get()));
        }
        this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.populateDefaultEquipmentSlots(pDifficulty);
        this.populateDefaultEquipmentEnchantments(pDifficulty);
        this.setCanPickUpLoot(this.random.nextFloat() < 0.45F * pDifficulty.getSpecialMultiplier());
        return pSpawnData;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 4, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.swinging) {
            if(Math.random() > 0.5) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skeleton_gladiator.attack0", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skeleton_gladiator.attack1", true));
            }
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            if(hasTarget()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skeleton_gladiator.chase", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skeleton_gladiator.walk", true));
            }
            return PlayState.CONTINUE;
        }
        if(hasTarget()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skeleton_gladiator.idle_chase", true));
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skeleton_gladiator.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        if(this.getTarget() != null) {
            this.entityData.set(HAS_TARGET, false);
        }
        super.tick();
    }

    @Override
    public void setTarget(@Nullable LivingEntity pLivingEntity) {
        if(pLivingEntity != null) this.entityData.set(HAS_TARGET, true);
        super.setTarget(pLivingEntity);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_TARGET, false);
    }

    public boolean hasTarget() {
        return this.entityData.get(HAS_TARGET);
    }


}

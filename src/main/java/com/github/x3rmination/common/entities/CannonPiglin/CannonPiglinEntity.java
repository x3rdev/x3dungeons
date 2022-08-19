package com.github.x3rmination.common.entities.CannonPiglin;

import com.github.x3rmination.common.items.PiglinCannonItem;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CannonPiglinEntity extends PiglinEntity implements IAnimatable, IRangedAttackMob {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    public CannonPiglinEntity(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0F, 160, 24.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 4, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cannon_piglin.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cannon_piglin.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected boolean isImmuneToZombification() {
        return true;
    }

    @Override
    public void setBaby(boolean pChildZombie) {
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        this.maybeWearArmor(EquipmentSlotType.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
        this.maybeWearArmor(EquipmentSlotType.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
        this.maybeWearArmor(EquipmentSlotType.FEET, new ItemStack(Items.GOLDEN_BOOTS));
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.PIGLIN_CANNON.get()));
    }

    public void maybeWearArmor(EquipmentSlotType pSlot, ItemStack pStack) {
        if (this.level.random.nextFloat() < 0.1F) {
            this.setItemSlot(pSlot, pStack);
        }
    }


    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        if(this.getMainHandItem().getItem() instanceof PiglinCannonItem) {
            this.setDeltaMovement(this.getViewVector(1.0F).scale(-1).add(0, 0.3, 0));
            FireballEntity fireballEntity = new FireballEntity(level, this, 0, 0, 0) {
                @Override
                public boolean shouldBlockExplode(Explosion pExplosion, IBlockReader pLevel, BlockPos pPos, BlockState pBlockState, float pExplosionPower) {
                    return false;
                }

                @Override
                protected void onHit(RayTraceResult pResult) {
                    if (!this.level.isClientSide) {
                        this.level.explode(null, this.getX(), this.getY(), this.getZ(), this.explosionPower, false, Explosion.Mode.NONE);
                        this.remove();
                    }
                }

                @Override
                protected float getInertia() {
                    return 1;
                }

                @Override
                public void tick() {
                    super.tick();
                    if(this.tickCount > 100) {
                        this.remove();
                    }
                }
            };
            fireballEntity.setDeltaMovement(this.getViewVector(1.0F));
            fireballEntity.explosionPower = 1;
            fireballEntity.setPos(fireballEntity.getX(), this.getY(0.5D) + 0.5D, fireballEntity.getZ());
            level.addFreshEntity(fireballEntity);
        }
    }
}

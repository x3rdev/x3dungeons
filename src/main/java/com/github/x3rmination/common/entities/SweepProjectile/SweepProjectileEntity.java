package com.github.x3rmination.common.entities.SweepProjectile;

import com.github.x3rmination.core.registry.EntityInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SweepProjectileEntity extends AbstractArrowEntity implements IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    private int damage = 2;

    public SweepProjectileEntity(EntityType<? extends SweepProjectileEntity> type, World world) {
        super(type, world);
    }

    public SweepProjectileEntity(LivingEntity pShooter, World pLevel) {
        super(EntityInit.SWEEP_PROJECTILE.get(), pShooter, pLevel);
//        this.setNoPhysics(true);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult pResult) {
        Entity entity = pResult.getEntity();
        entity.hurt(DamageSource.MAGIC, this.damage);
        entity.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        pResult.getEntity().level.playSound(null, entity.blockPosition(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundCategory.HOSTILE, 2F, 3F);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult pResult) {
        this.remove();
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 4, this::predicate));
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount > 40) this.remove();
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sweep_projectile.grow", false));
        return PlayState.CONTINUE;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int amount) {
        this.damage = amount;
    }

    @Override
    protected float getWaterInertia() {
        return 1;
    }
}

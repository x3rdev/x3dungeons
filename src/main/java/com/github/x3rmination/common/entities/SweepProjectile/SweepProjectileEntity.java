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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
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

    // Epic fight fixes

    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vector3d vector3d = (new Vector3d(pX, pY, pZ)).normalize().add(this.random.nextGaussian() * (double)0.0075F * (double)pInaccuracy, this.random.nextGaussian() * (double)0.0075F * (double)pInaccuracy, this.random.nextGaussian() * (double)0.0075F * (double)pInaccuracy).scale((double)pVelocity).normalize();
        this.setDeltaMovement(vector3d);
        float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
        this.yRot = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
        this.xRot = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
        this.yRotO = this.yRot;
        this.xRotO = this.xRot;
    }

    @Override
    public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        if(pShooter instanceof PlayerEntity && ModList.get().isLoaded("epicfight")) {
            pX = MathHelper.wrapDegrees(pX - 180);
        }
        float f = -MathHelper.sin(pY * ((float)Math.PI / 180F)) * MathHelper.cos(pX * ((float)Math.PI / 180F));
        float f1 = -MathHelper.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = MathHelper.cos(pY * ((float)Math.PI / 180F)) * MathHelper.cos(pX * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, pVelocity, pInaccuracy);
        Vector3d vector3d = pShooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vector3d.x, pShooter.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
    }
}

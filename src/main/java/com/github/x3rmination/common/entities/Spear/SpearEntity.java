package com.github.x3rmination.common.entities.Spear;

import com.github.x3rmination.common.items.SpearItem;
import com.github.x3rmination.core.registry.EntityInit;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpearEntity extends AbstractArrowEntity implements IEntityAdditionalSpawnData {


    private ItemStack thrownStack = new ItemStack(ItemInit.WOODEN_SPEAR.get());
    private ItemTier itemTier;
    private boolean dealtDamage;
    private static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.defineId(SpearEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> IS_ENCHANTED = EntityDataManager.defineId(SpearEntity.class, DataSerializers.BOOLEAN);
    public int returningTicks;

    public SpearEntity(EntityType<? extends SpearEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SpearEntity(World worldIn, LivingEntity shooter, ItemStack thrownStackIn) {
        super(EntityInit.SPEAR.get(), shooter, worldIn);
        this.thrownStack = thrownStackIn;
        this.itemTier = (ItemTier) ((SpearItem) thrownStackIn.getItem()).getTier();
        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(thrownStackIn));
        this.entityData.set(IS_ENCHANTED, thrownStackIn.hasFoil());
    }

    @Override
    public ItemStack getPickupItem() {
        return thrownStack;
    }

    public ItemTier getItemTier(){
        if(itemTier == null){
            return (ItemTier) ((SpearItem) this.thrownStack.getItem()).getTier();
        }
        return this.itemTier;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOYALTY_LEVEL, (byte)0);
        this.entityData.define(IS_ENCHANTED, false);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeEnum(getItemTier());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.itemTier = additionalData.readEnum(ItemTier.class);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            int i = this.entityData.get(LOYALTY_LEVEL);
            if (i > 0 && !this.shouldReturnToThrower()) {
                if (!this.level.isClientSide && this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.remove();
            } else if (i > 0) {
                this.setNoPhysics(true);
                Vector3d vector3d = new Vector3d(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vector3d.y * 0.015D * i, this.getZ());
                if (this.level.isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(d0)));
                if (this.returningTicks == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returningTicks;
            }
        }

        super.tick();
    }

    private boolean shouldReturnToThrower() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        float f = this.getItemTier().getAttackDamageBonus() + 6;
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            f += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource = DamageSource.arrow(this, (entity1 == null ? this : entity1));
        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
                }

                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isEnchanted() {
        return this.entityData.get(IS_ENCHANTED);
    }
}

package com.github.x3rmination.common.entities.CasterPiglin;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinAction;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;



@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID)
public class CasterPiglinEntity extends PiglinEntity implements IAnimatable, IRangedAttackMob {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    public CasterPiglinEntity(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected boolean canHunt() {
        return false;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 0.9F, 15, 24.0F));
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
        if (Math.abs(this.getDeltaMovement().x) > 0.01 || Math.abs(this.getDeltaMovement().z) > 0.01) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.caster_piglin.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.caster_piglin.idle", true));
        return PlayState.CONTINUE;
    }

    @SubscribeEvent
    public static void tickEvent(LivingEquipmentChangeEvent event) {
        if(event.getEntity() instanceof AbstractPiglinEntity) {
            AbstractPiglinEntity piglinEntity = ((AbstractPiglinEntity) event.getEntity());
            ItemStack stack = piglinEntity.getItemBySlot(EquipmentSlotType.HEAD);
            if (stack.getItem().equals(ItemInit.EXPLORER_BANNER.get())) {
                piglinEntity.getEntityData().set(AbstractPiglinEntity.DATA_IMMUNE_TO_ZOMBIFICATION, true);
            }
        }
    }

    @Override
    protected boolean isImmuneToZombification() {
        return true;
    }

    @Override
    public PiglinAction getArmPose() {
        return null;
    }

    @Override
    protected void playConvertedSound() {

    }

    @Override
    public void setBaby(boolean pChildZombie) {
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pVelocity) {
        float nextFloat = this.random.nextFloat();
        if(this.distanceTo(target) < 4) {
            if(nextFloat < 0.5) {
                performFireBreathAttack();
                return;
            } else {
                performEvade();
            }
        }
        if(nextFloat < 0.7) {
            performFireAttack();
        } else {
            performGoldAttack();
        }
    }

    private void performEvade() {
        this.addEffect(new EffectInstance(Effects.LEVITATION, 20, 6));
        this.addEffect(new EffectInstance(Effects.SLOW_FALLING, 30 * 20));
        if(this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(), this.getZ(), 5, 0, 0, 0, 0.1);
        }
    }

    private void performFireBreathAttack() {
        double radianYRot = (MathHelper.wrapDegrees(this.yHeadRot) + 90) * Math.PI / 180;
        spawnFireRing(new Vector3d(this.position().x + (2 * Math.cos(radianYRot)), this.getY() + 1, this.position().z + (2 * Math.sin(radianYRot))));

    }

    private void performFireAttack() {
        double radianYRot = (MathHelper.wrapDegrees(this.yHeadRot) + 90) * Math.PI / 180;
        for(int i = 0; i < 50; i++) {
            Vector3d pos = new Vector3d(this.position().x + ((0.5 + i/5F) * Math.cos(radianYRot)), this.position().y + 1 + ((0.5 + i/5F) * Math.sin(MathHelper.wrapDegrees(this.getViewXRot(0)) * Math.PI / -180)), this.position().z + ((0.5 + i/5F) * Math.sin(radianYRot)));
            System.out.println(this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
            List<Entity> entities = this.level.getEntities(this, new AxisAlignedBB(pos.x - 0.5, pos.y - 0.5, pos.z - 0.5, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5));
            entities.forEach(entity ->  {
                if(!(entity instanceof AbstractPiglinEntity)) {
                    entity.setSecondsOnFire(4);
                    entity.setDeltaMovement(this.getViewVector(0));
                }
            });
            spawnFireParticle(pos, 0.01);
        }
    }

    private void performGoldAttack() {
        List<Entity> entities = this.level.getEntities(this, new AxisAlignedBB(this.getX() - 4, this.getY() - 4, this.getZ() - 4, this.getX() + 4, this.getY() + 4, this.getZ() + 4));
        for(Entity piglinEntity : entities) {
            if(piglinEntity instanceof AbstractPiglinEntity) {
                ((AbstractPiglinEntity) piglinEntity).addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE));
            }
        }
    }

    private void spawnFireRing(Vector3d origin) {
        List<Entity> entities = this.level.getEntities(this, new AxisAlignedBB(origin.x - 2, origin.y - 2, origin.z - 2, origin.x + 2, origin.y + 2, origin.z + 2));
        entities.forEach(entity -> {
            if(!(entity instanceof AbstractPiglinEntity)) {
                entity.setSecondsOnFire(10);
                entity.hurt(DamageSource.MAGIC, 1);
            }
        });
        for(int i = 0; i < 50; i++) {
            spawnFireParticle(origin, 0.1);
        }

    }

    private void spawnFireParticle(Vector3d pos, double speed) {
        if(this.level instanceof ServerWorld) {
            ((ServerWorld) this.level).sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 1, 0, 0, 0, speed);
        }
    }
}

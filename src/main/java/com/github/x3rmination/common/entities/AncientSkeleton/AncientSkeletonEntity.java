package com.github.x3rmination.common.entities.AncientSkeleton;

import com.github.x3rmination.common.items.AncientSword.AncientSwordItem;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Collection;

public class AncientSkeletonEntity extends MonsterEntity implements IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    private final ServerBossInfo bossEvent = (ServerBossInfo)(new ServerBossInfo(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS)).setDarkenScreen(true);


    public AncientSkeletonEntity(EntityType<? extends AncientSkeletonEntity> type, World world) {
        super(type, world);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1D, false));
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
        chestStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
        ItemStack legStack = new ItemStack(Items.DIAMOND_LEGGINGS);
        legStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
        ItemStack bootStack = new ItemStack(Items.DIAMOND_BOOTS);
        bootStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);

        this.setItemSlot(EquipmentSlotType.CHEST, chestStack);
        this.setItemSlot(EquipmentSlotType.LEGS, legStack);
        this.setItemSlot(EquipmentSlotType.FEET, bootStack);
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.ANCIENT_SWORD.get()));
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 4, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ancient_skeleton.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ancient_skeleton.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void customServerAiStep() {
        this.bossEvent.setPercent(this.getHealth() / this.getMaxHealth());
        super.customServerAiStep();
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
    public void setGuaranteedDrop(EquipmentSlotType pSlot) {
        this.setDropChance(EquipmentSlotType.HEAD, 1);
        super.setGuaranteedDrop(pSlot);
    }

    @Override
    public void aiStep() {

    }
}

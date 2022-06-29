package com.github.x3rmination.common.entities.AncientSkeleton;

import com.github.x3rmination.common.entities.GladiatorSkeleton.GladiatorSkeletonEntity;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.PowerEnchantment;
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
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class AncientSkeletonEntity extends MonsterEntity implements IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);

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
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.5D, false));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
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
        ItemStack chestStack = new ItemStack(Items.DIAMOND_CHESTPLATE);
        chestStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
        ItemStack legStack = new ItemStack(Items.DIAMOND_LEGGINGS);
        legStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
        ItemStack helmetStack = new ItemStack(Items.DIAMOND_BOOTS);
        helmetStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);

        this.setItemSlot(EquipmentSlotType.CHEST, helmetStack);
        this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
        this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(Items.DIAMOND_BOOTS));
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

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}

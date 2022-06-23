package com.github.x3rmination.common.items;

import com.github.x3rmination.common.entities.SpearEntity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpearItem extends TieredItem implements IVanishable {

    private final Multimap<Attribute, AttributeModifier> spearAttributes;
    private final int maxUseDuration;

    public SpearItem(IItemTier tier, Properties properties, float baseDamage, int maxUseDuration) {
        super(tier, properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", tier.getAttackDamage() + 4, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.9F, AttributeModifier.Operation.ADDITION));
        this.maxUseDuration = maxUseDuration;
        this.spearAttributes = builder.build();

    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entityLiving;
            int charge = this.getUseDuration(stack) - timeLeft;
            if(charge > 5 && !worldIn.isRemote) {
                stack.damageItem(1, playerentity, player -> player.sendBreakAnimation(entityLiving.getActiveHand()));

                SpearEntity spearEntity = new SpearEntity(worldIn, playerentity, stack);
                float maxUse = getUseDuration(stack);
                float currentTime = maxUse-timeLeft;
                float ratio = Math.min(currentTime*1000/maxUse, 1);
                spearEntity.setDirectionAndMovement(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, 1.5F * ratio, 1.0F);
                if (playerentity.abilities.isCreativeMode) {
                    spearEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }

                worldIn.addEntity(spearEntity);
                if (!playerentity.abilities.isCreativeMode) {
                    playerentity.inventory.deleteStack(stack);
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return maxUseDuration;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, entity -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving, entity -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.spearAttributes : super.getAttributeModifiers(equipmentSlot);
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public IItemTier getTier() {
        return super.getTier();
    }

}

package com.github.x3rmination.common.items;

import com.github.x3rmination.common.entities.Spear.SpearEntity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.enchantment.LoyaltyEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class SpearItem extends TieredItem implements IVanishable {

    private final Multimap<Attribute, AttributeModifier> spearAttributes;
    private final int maxUseDuration;

    public SpearItem(IItemTier tier, Properties properties) {
        super(tier, properties.durability(tier.getUses()));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", tier.getAttackDamageBonus() + 5, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.9F, AttributeModifier.Operation.ADDITION));
        this.maxUseDuration = 10000;
        this.spearAttributes = builder.build();
    }

    @Override
    public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) entityLiving;
            int charge = this.getUseDuration(stack) - timeLeft;
            if(charge > 5 && !worldIn.isClientSide) {
                stack.hurtAndBreak(1, playerentity, player -> player.broadcastBreakEvent(entityLiving.getUsedItemHand()));

                SpearEntity spearEntity = new SpearEntity(worldIn, playerentity, stack);
                float maxUse = getUseDuration(stack);
                float currentTime = maxUse-timeLeft;
                float ratio = Math.min(currentTime*1000/maxUse, 1);
                spearEntity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 1.5F * ratio, 1.0F);
                if (playerentity.abilities.instabuild) {
                    spearEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }

                worldIn.addFreshEntity(spearEntity);
                if (!playerentity.abilities.instabuild) {
                    playerentity.inventory.removeItem(stack);
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return ActionResult.consume(itemstack);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return maxUseDuration;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0D) {
            stack.hurtAndBreak(2, entityLiving, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public IItemTier getTier() {
        return super.getTier();
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if(enchantment.category == (EnchantmentType.WEAPON)) {
            return true;
        }
        if(enchantment instanceof LoyaltyEnchantment) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }


}

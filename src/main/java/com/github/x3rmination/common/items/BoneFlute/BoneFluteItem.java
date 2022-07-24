package com.github.x3rmination.common.items.BoneFlute;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class BoneFluteItem extends Item {
    public BoneFluteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        return ActionResult.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, World pLevel, LivingEntity pEntityLiving) {
        if(pEntityLiving instanceof PlayerEntity) ((PlayerEntity) pEntityLiving).getCooldowns().addCooldown(this, 400);
        pLevel.playSound(null, pEntityLiving.blockPosition(), SoundEvents.BELL_RESONATE, SoundCategory.PLAYERS, 4F, 0.1F);
        AxisAlignedBB box = new AxisAlignedBB(pEntityLiving.blockPosition().north(10).east(10).above(10), pEntityLiving.blockPosition().south(20).west(20).below(20));
        pLevel.getEntities(pEntityLiving, box).forEach(entity -> {
            if(entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new EffectInstance(Effects.CONFUSION, 150));
                ((LivingEntity) entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 4));
            }
        });
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 50;
    }

    @Override
    public UseAction getUseAnimation(ItemStack pStack) {
        return UseAction.BOW;
    }


}

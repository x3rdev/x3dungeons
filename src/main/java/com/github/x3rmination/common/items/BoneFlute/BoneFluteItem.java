package com.github.x3rmination.common.items.BoneFlute;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BoneFluteItem extends Item {
    public BoneFluteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, World pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        pLevel.playSound(null, pEntityLiving.blockPosition(), SoundEvents.BELL_RESONATE, SoundCategory.PLAYERS, 4F, 1F);
        super.releaseUsing(pStack, pLevel, pEntityLiving, pTimeLeft);
    }


    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        pPlayer.getCooldowns().addCooldown(this, 100);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();

        if (!pPlayer.abilities.instabuild && !flag) {
            return ActionResult.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return ActionResult.consume(itemstack);
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 1000;
    }
}

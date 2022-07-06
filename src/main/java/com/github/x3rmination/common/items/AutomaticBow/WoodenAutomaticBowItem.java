package com.github.x3rmination.common.items.AutomaticBow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WoodenAutomaticBowItem extends BowItem {

    public WoodenAutomaticBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 7200;
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if(pEntity instanceof LivingEntity) this.releaseUsing(pStack, pLevel, (LivingEntity) pEntity, 0);
    }
}

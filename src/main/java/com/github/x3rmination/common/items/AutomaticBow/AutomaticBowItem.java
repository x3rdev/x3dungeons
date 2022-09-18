package com.github.x3rmination.common.items.AutomaticBow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AutomaticBowItem extends BowItem {
    protected int progress;
    protected int threshold;
    private boolean autoUse;
    public AutomaticBowItem(Properties properties, int threshold) {
        super(properties);
        this.threshold = threshold;
        this.progress = threshold;
    }

    @Override
    public boolean isRepairable(ItemStack p_isRepairable_1_) {
        return true;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getThreshold() {
        return this.threshold;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(player instanceof PlayerEntity) {
            if (progress < threshold) {
                progress++;
            } else {
                autoUse = true;
                this.releaseUsing(stack, player.level, player, 0);
                autoUse = false;
                player.stopUsingItem();
                progress = 0;
            }
        }
        super.onUsingTick(stack, player, count);
    }

    @Override
    public void releaseUsing(ItemStack pStack, World pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if(!autoUse) {
            pTimeLeft = 72000;
            progress = 0;
        }
        super.releaseUsing(pStack, pLevel, pEntityLiving, pTimeLeft);
    }
}

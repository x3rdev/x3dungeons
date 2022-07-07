package com.github.x3rmination.common.items.AutomaticBow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AutomaticBowItem extends BowItem {
    protected int progress;
    protected int threshold;
    public AutomaticBowItem(Properties properties, int threshold) {
        super(properties);
        this.threshold = threshold;
        this.progress = threshold;
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
                this.releaseUsing(stack, player.level, player, 0);
                player.stopUsingItem();
                progress = 0;
            }
        }
        super.onUsingTick(stack, player, count);
    }

}

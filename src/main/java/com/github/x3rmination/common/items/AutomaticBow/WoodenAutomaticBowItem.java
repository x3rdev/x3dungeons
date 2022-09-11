package com.github.x3rmination.common.items.AutomaticBow;

import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;

public class WoodenAutomaticBowItem extends AutomaticBowItem {

    public WoodenAutomaticBowItem(Properties properties, int threshold) {
        super(properties, threshold);
    }

    @Override
    public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack ingredient) {
        return ItemTags.WOODEN_SLABS.contains(ingredient.getItem());
    }

    @Override
    public boolean isRepairable(ItemStack p_isRepairable_1_) {
        return super.isRepairable(p_isRepairable_1_);
    }
}

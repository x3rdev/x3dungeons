package com.github.x3rmination.common.items.AutomaticBow;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class NetheriteAutomaticBowItem extends AutomaticBowItem{

    public NetheriteAutomaticBowItem(Properties properties, int threshold) {
        super(properties, threshold);
    }

    @Override
    public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack ingredient) {
        return ingredient.getItem().equals(Items.NETHERITE_INGOT);
    }
}

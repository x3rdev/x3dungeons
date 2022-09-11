package com.github.x3rmination.common.items.AutomaticBow;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class GoldenAutomaticBowItem extends AutomaticBowItem{

    public GoldenAutomaticBowItem(Item.Properties properties, int threshold) {
        super(properties, threshold);
    }

    @Override
    public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack ingredient) {
        return ingredient.getItem().equals(Items.GOLD_INGOT);
    }
}

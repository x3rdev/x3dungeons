package com.github.x3rmination.common.items.AutomaticBow;

import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;

public class StoneAutomaticBowItem extends AutomaticBowItem{

    public StoneAutomaticBowItem(Properties properties, int threshold) {
        super(properties, threshold);
    }

    @Override
    public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack ingredient) {
        return ItemTags.STONE_TOOL_MATERIALS.contains(ingredient.getItem());
    }
}

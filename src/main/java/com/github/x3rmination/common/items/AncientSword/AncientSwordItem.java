package com.github.x3rmination.common.items.AncientSword;

import com.github.x3rmination.common.items.ItemTiers;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.item.SwordItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AncientSwordItem extends SwordItem implements IVanishable, IAnimatable {

    public AncientSwordItem(Properties properties) {
        super(ItemTiers.ANCIENT_SWORD, 10, -3, properties);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}

package com.github.x3rmination.common.items.GoldenShield;

import com.github.x3rmination.X3DUNGEONS;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID)
public class GoldenShield extends ShieldItem {

    public GoldenShield(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(World pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pCount) {
        pLivingEntity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 10));
        super.onUseTick(pLevel, pLivingEntity, pStack, pCount);
    }
}

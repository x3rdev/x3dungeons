package com.github.x3rmination.common.entities;

import com.github.x3rmination.core.registry.EntityInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpearEntity extends AbstractArrowEntity {

    private ItemStack thrownStack = new ItemStack(Items.TRIDENT);
    private boolean dealtDamage;

    public SpearEntity(EntityType<? extends SpearEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SpearEntity(World worldIn, LivingEntity shooter, ItemStack thrownStackIn) {
        super(EntityInit.SPEAR.get(), shooter, worldIn);
        this.thrownStack = thrownStackIn.copy();
    }

    @OnlyIn(Dist.CLIENT)
    public SpearEntity(EntityType<? extends SpearEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }
}

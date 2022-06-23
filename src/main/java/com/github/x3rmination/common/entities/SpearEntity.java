package com.github.x3rmination.common.entities;

import com.github.x3rmination.common.items.SpearItem;
import com.github.x3rmination.core.registry.EntityInit;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpearEntity extends AbstractArrowEntity implements IEntityAdditionalSpawnData {


    private ItemStack thrownStack = new ItemStack(ItemInit.WOODEN_SPEAR.get());
    private ItemTier itemTier;


    public SpearEntity(EntityType<? extends SpearEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SpearEntity(World worldIn, LivingEntity shooter, ItemStack thrownStackIn) {
        super(EntityInit.SPEAR.get(), shooter, worldIn);
        this.thrownStack = thrownStackIn;
        this.itemTier = (ItemTier) ((SpearItem) thrownStackIn.getItem()).getTier();
    }

    @Override
    public ItemStack getArrowStack() {
        return thrownStack;
    }

    public ItemTier getItemTier(){
        if(itemTier == null){
            return (ItemTier) ((SpearItem) this.thrownStack.getItem()).getTier();
        }
        return this.itemTier;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeEnumValue(getItemTier());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.itemTier = additionalData.readEnumValue(ItemTier.class);
    }
}

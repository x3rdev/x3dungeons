package com.github.x3rmination.common.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class PiglinCannonItem extends Item {
    public PiglinCannonItem(Properties pProperties) {
        super(pProperties.durability(40));
    }

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity player, Hand pHand) {
        ItemStack itemstack = player.getItemInHand(pHand);
        boolean flag = false;
        for(ItemStack stack : player.inventory.items) {
            if(stack.getItem() instanceof FireChargeItem) {
                flag = true;
                break;
            }
        }

        if (!player.isCreative() && flag) {
            return ActionResult.fail(itemstack);
        } else {
            player.startUsingItem(pHand);
            return ActionResult.consume(itemstack);
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAction getUseAnimation(ItemStack pStack) {
        return UseAction.BOW;
    }

    @Override
    public void releaseUsing(ItemStack stack, World level, LivingEntity entity, int timeLeft) {
        stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        FireballEntity fireballEntity = new FireballEntity(level, entity, 0, 0, 0) {
            @Override
            public boolean shouldBlockExplode(Explosion pExplosion, IBlockReader pLevel, BlockPos pPos, BlockState pBlockState, float pExplosionPower) {
                return false;
            }

            @Override
            protected void onHit(RayTraceResult pResult) {
                if (!this.level.isClientSide) {
                    this.level.explode(null, this.getX(), this.getY(), this.getZ(), this.explosionPower, false, Explosion.Mode.NONE);
                    this.remove();
                }
            }

            @Override
            protected float getInertia() {
                return 1;
            }

            @Override
            public void tick() {
                super.tick();
                if(this.tickCount > 100) {
                    this.remove();
                }
            }
        };
        fireballEntity.setDeltaMovement(entity.getLookAngle().scale(1.5));
        fireballEntity.explosionPower = Math.round((float) Math.min(100, this.getUseDuration(stack) - timeLeft) / 20);
        fireballEntity.setPos(fireballEntity.getX(), entity.getY(0.5D) + 0.5D, fireballEntity.getZ());
        level.addFreshEntity(fireballEntity);
    }
}

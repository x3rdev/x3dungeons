package com.github.x3rmination.common.items.AncientSword;

import com.github.x3rmination.common.entities.SweepProjectile.SweepProjectileEntity;
import com.github.x3rmination.common.items.ItemTiers;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.example.item.JackInTheBoxItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AncientSwordItem extends SwordItem implements IVanishable, IAnimatable, ISyncable {
    public AnimationFactory factory = new AnimationFactory(this);

    public AncientSwordItem(Properties properties) {
        super(ItemTiers.ANCIENT_SWORD, 10, -3, properties);
        GeckoLibNetwork.registerSyncable(this);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2, this::predicate));

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {

    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide) {
            final ItemStack stack = player.getItemInHand(hand);
            final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
            final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player);
            GeckoLibNetwork.syncAnimation(target, this, id, 0);
        }
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return ActionResult.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, World level, LivingEntity entityLiving, int pTimeLeft) {
        int useTime = this.getUseDuration(stack) - pTimeLeft;
        if(useTime > 100) useTime = 100;
        float usePercentage =  ((float) useTime) / 100;
        if(level instanceof ServerWorld) {
            SweepProjectileEntity sweepEntity = new SweepProjectileEntity(entityLiving, level);
            sweepEntity.shootFromRotation(entityLiving, entityLiving.xRot, entityLiving.yHeadRot, 0.0F, 3 * usePercentage, 0);
            System.out.println(entityLiving.yHeadRot);
            sweepEntity.setNoGravity(true);
            sweepEntity.setDamage(4);
            level.addFreshEntity(sweepEntity);
        }
        level.playSound(null, entityLiving.blockPosition(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundCategory.HOSTILE ,6, 3F);
        if(entityLiving instanceof PlayerEntity) ((PlayerEntity) entityLiving).getCooldowns().addCooldown(this, 10);
        super.releaseUsing(stack, level, entityLiving, pTimeLeft);
    }

    @Override
    public UseAction getUseAnimation(ItemStack pStack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
}

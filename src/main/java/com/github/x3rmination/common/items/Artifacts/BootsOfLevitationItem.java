package com.github.x3rmination.common.items.Artifacts;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.items.Artifacts.attribute.CompactAttribute;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID)
public class BootsOfLevitationItem extends ArtifactItem {
    public BootsOfLevitationItem(Properties properties, CompactAttribute... compactAttribute) {
        super(properties, compactAttribute);
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if(!CuriosApi.getCuriosHelper().findCurios(player, ItemInit.BOOTS_OF_LEVITATION.get()).isEmpty()) {
            World world = event.player.level;
            int x = (int) Math.floor(player.getX());
            int y = (int) (player.getY() - player.getMyRidingOffset());
            int z = (int) Math.floor(player.getZ());
            BlockPos pos = new BlockPos(x, y, z);
            if (world.getBlockState(event.player.blockPosition().below()).getFluidState() != Fluids.EMPTY.defaultFluidState()) {
                if (!player.isShiftKeyDown() && player.level.isEmptyBlock(pos)) {
                    player.setDeltaMovement(player.getDeltaMovement().multiply(1, 0, 1));
                    player.fallDistance = 0;
                    player.setOnGround(true);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new TranslationTextComponent("tooltip.x3dungeons.boots_of_levitation"));
    }



}

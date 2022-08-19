package com.github.x3rmination.common.items.Artifacts;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.items.Artifacts.attribute.CompactAttribute;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID)

public class OrbOfInhibitionItem extends ArtifactItem {

    public OrbOfInhibitionItem(Properties properties, CompactAttribute... compactAttribute) {
        super(properties);
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.PlayerTickEvent event) {

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new TranslationTextComponent("x3dungeons.orb_of_inhibition.tooltip"));
    }
}

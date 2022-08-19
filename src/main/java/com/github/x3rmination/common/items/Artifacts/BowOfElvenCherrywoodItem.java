package com.github.x3rmination.common.items.Artifacts;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.items.Artifacts.attribute.CompactAttribute;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID)
public class BowOfElvenCherrywoodItem extends ArtifactItem {
    public BowOfElvenCherrywoodItem(Properties pProperties, CompactAttribute... compactAttribute) {
        super(pProperties, compactAttribute);
    }

    @SubscribeEvent
    public static void damageEvent(LivingHurtEvent event) {
        if(event.getSource().isProjectile() ) {
            for(SlotResult slotResult : CuriosApi.getCuriosHelper().findCurios(event.getEntityLiving())) {
                if(slotResult.getStack().getItem() instanceof BowOfElvenCherrywoodItem) {
                    event.setAmount(event.getAmount() * 1.05F);
                    return;
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new TranslationTextComponent("x3dungeons.bow_of_elven_cherrywood.tooltip"));
    }
}

package com.github.x3rmination.common.items.Artifacts;

import com.github.x3rmination.common.items.Artifacts.attribute.CompactAttribute;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.common.capability.CurioItemCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ArtifactItem extends Item {

    private final Multimap<Attribute, AttributeModifier> attributeMap = LinkedHashMultimap.create();
    private ITextComponent defaultTooltip;

    public ArtifactItem(Properties properties, CompactAttribute... compactAttribute) {
        this(properties, null, compactAttribute);
    }

    public ArtifactItem(Properties properties, @Nullable ITextComponent tooltip, CompactAttribute... compactAttribute) {
        super(properties.stacksTo(1));
        defaultTooltip = tooltip;
        if(compactAttribute != null) {
            List<CompactAttribute> compactAttributeList = java.util.Arrays.asList(compactAttribute);
            putAttributes(compactAttributeList);
        }
    }

    private void putAttributes(List<CompactAttribute> list) {
        for (CompactAttribute attribute : list) {
            attributeMap.put(attribute.getAttribute(), new AttributeModifier(UUID.randomUUID(), attribute.getName(), attribute.getModifier(), attribute.getOperation()));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if(defaultTooltip != null) {
            pTooltip.add(defaultTooltip);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return CurioItemCapability.createProvider(new ICurio() {

//            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
//                if (!livingEntity.getCommandSenderWorld().isClientSide && livingEntity.tickCount % 19 == 0) {
//                    livingEntity.addEffect(new EffectInstance(Effects.DIG_SPEED, 20, 0, true, true));
//                }
//            }

            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
                return attributeMap;
            }

            @Nonnull
            public DropRule getDropRule(LivingEntity livingEntity) {
                return DropRule.ALWAYS_DROP;
            }

            @Nonnull
            public SoundInfo getEquipSound(SlotContext slotContext) {
                return new SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 1.0F);
            }

            public boolean canEquipFromUse(SlotContext slot) {
                return true;
            }
        });
    }
}

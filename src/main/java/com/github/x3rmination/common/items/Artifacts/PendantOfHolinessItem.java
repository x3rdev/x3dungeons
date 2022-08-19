package com.github.x3rmination.common.items.Artifacts;

import com.github.x3rmination.core.registry.ItemInit;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.VanishingCurseEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.fixes.ItemStackEnchantmentFix;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.common.CuriosHelper;
import top.theillusivec4.curios.common.capability.CurioItemCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class PendantOfHolinessItem extends Item {

    public PendantOfHolinessItem(Properties pProperties) {
        super(pProperties.durability(20));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new TranslationTextComponent("x3dungeons.pendant_of_holiness.tooltip"));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return CurioItemCapability.createProvider(new ICurio() {

            @Nonnull
            public DropRule getDropRule(LivingEntity livingEntity) {
                return DropRule.ALWAYS_DROP;
            }

            @Nonnull
            public SoundInfo getEquipSound(SlotContext slotContext) {
                return new SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 1.0F);
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                ICurio.super.onEquip(slotContext, prevStack);
                LivingEntity wearer = slotContext.getWearer();
                if(wearer instanceof PlayerEntity && !wearer.level.isClientSide) {
                    PlayerEntity player = ((PlayerEntity) wearer);
                    Optional<SlotResult> optionalPendant = CuriosApi.getCuriosHelper().findFirstCurio(wearer, ItemInit.PENDANT_OF_HOLINESS.get());
                    ArrayList<ItemStack> wholeInventory = new ArrayList<>();
                    wholeInventory.addAll(player.inventory.items);
                    wholeInventory.addAll(player.inventory.offhand);
                    wholeInventory.addAll(player.inventory.armor);
                    for(ItemStack stack : wholeInventory) {
                        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter((p_217012_0_) -> !p_217012_0_.getKey().isCurse()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        if(!EnchantmentHelper.getEnchantments(stack).equals(map)) {
                            if(optionalPendant.isPresent()) {
                                ItemStack pendant = optionalPendant.orElse(null).getStack();
                                pendant.hurtAndBreak(1, wearer, entity -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                            }
                            ITextComponent text = ((new TranslationTextComponent("x3dungeons.pendant_of_holiness.equip")).withStyle(Style.EMPTY.withColor(Color.parseColor("0xc8b087")))).append(stack.getDisplayName());
                            wearer.sendMessage(text, Util.NIL_UUID);

                            wearer.level.getServer().getLevel(wearer.level.dimension()).sendParticles(ParticleTypes.TOTEM_OF_UNDYING, wearer.getX(), wearer.getY(), wearer.getZ(), 10, 0, 1, 0, 0.5F);
                            player.level.playSound(null, player.blockPosition(), SoundEvents.CONDUIT_ACTIVATE, SoundCategory.BLOCKS, 1, 1);
                            EnchantmentHelper.setEnchantments(map, stack);
                        }
                    }
                }
            }

            public boolean canEquipFromUse(SlotContext slot) {
                return true;
            }
        });
    }
}

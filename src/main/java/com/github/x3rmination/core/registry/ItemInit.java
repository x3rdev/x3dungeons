package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.client.ister.SpearISTER;
import com.github.x3rmination.common.items.SpearItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, X3DUNGEONS.MOD_ID);

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new SpearItem(ItemTier.WOOD, (new Item.Properties()).group(ModItemTab.instance).maxDamage(250).setISTER(()-> SpearISTER::new), 1, 72000));

    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new SpearItem(ItemTier.STONE, (new Item.Properties()).group(ModItemTab.instance).maxDamage(250), 2, 72000));

    public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
            () -> new SpearItem(ItemTier.GOLD, (new Item.Properties()).group(ModItemTab.instance).maxDamage(250), 3, 72000));

    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new SpearItem(ItemTier.IRON, (new Item.Properties()).group(ModItemTab.instance).maxDamage(250), 4, 72000));

    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new SpearItem(ItemTier.DIAMOND, (new Item.Properties()).group(ModItemTab.instance).maxDamage(250), 5, 72000));

    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new SpearItem(ItemTier.NETHERITE, (new Item.Properties()).group(ModItemTab.instance).maxDamage(250), 6, 72000));

    public static class ModItemTab extends ItemGroup {
        public static final ModItemTab instance = new ModItemTab(ItemGroup.GROUPS.length, X3DUNGEONS.MOD_ID);
        private ModItemTab(int index, String tabName) {
            super(index, tabName);
            this.hasSearchBar();
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(WOODEN_SPEAR.get());
        }
    }
}

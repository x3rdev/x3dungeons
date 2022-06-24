package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.items.SpearItem;
import net.minecraft.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, X3DUNGEONS.MOD_ID);

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new SpearItem(ItemTier.WOOD, (new Item.Properties()).tab(ModItemTab.instance).durability(250), 1));

    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new SpearItem(ItemTier.STONE, (new Item.Properties()).tab(ModItemTab.instance).durability(250), 2));

    public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
            () -> new SpearItem(ItemTier.GOLD, (new Item.Properties()).tab(ModItemTab.instance).durability(250), 3));

    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new SpearItem(ItemTier.IRON, (new Item.Properties()).tab(ModItemTab.instance).durability(250), 4));

    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new SpearItem(ItemTier.DIAMOND, (new Item.Properties()).tab(ModItemTab.instance).durability(250), 5));

    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new SpearItem(ItemTier.NETHERITE, (new Item.Properties()).tab(ModItemTab.instance).durability(250), 6));

    public static final RegistryObject<Item> SKELETON_GLADIATOR_SPAWN_EGG = ITEMS.register("skeleton_gladiator_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.SKELETON_GLADIATOR, 0xc4c4c4, 0xebce2a, (new Item.Properties()).tab(ModItemTab.instance)));

    public static class ModItemTab extends ItemGroup {
        public static final ModItemTab instance = new ModItemTab(ItemGroup.TABS.length, X3DUNGEONS.MOD_ID);
        private ModItemTab(int index, String tabName) {
            super(index, tabName);
            this.hasSearchBar();
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(WOODEN_SPEAR.get());
        }
    }
}

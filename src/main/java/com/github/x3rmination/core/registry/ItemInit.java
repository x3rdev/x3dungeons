package com.github.x3rmination.core.registry;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.items.AncientSword.AncientSwordItem;
import com.github.x3rmination.common.items.AncientSword.AncientSwordRenderer;
import com.github.x3rmination.common.items.AutomaticBow.*;
import com.github.x3rmination.common.items.BoneFlute.BoneFluteItem;
import com.github.x3rmination.common.items.SpearItem;
import net.minecraft.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    // ADD EPIC FIGHT MOD INTEGRATION, AVAILABLE ON CURSEFORGE PAGE

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, X3DUNGEONS.MOD_ID);

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new SpearItem(ItemTier.WOOD, (new Item.Properties()).tab(ModItemTab.instance)));

    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new SpearItem(ItemTier.STONE, (new Item.Properties()).tab(ModItemTab.instance)));

    public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
            () -> new SpearItem(ItemTier.GOLD, (new Item.Properties()).tab(ModItemTab.instance)));

    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new SpearItem(ItemTier.IRON, (new Item.Properties()).tab(ModItemTab.instance)));

    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new SpearItem(ItemTier.DIAMOND, (new Item.Properties()).tab(ModItemTab.instance)));

    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new SpearItem(ItemTier.NETHERITE, (new Item.Properties()).tab(ModItemTab.instance)));

    public static final RegistryObject<Item> WOODEN_AUTOMATIC_BOW = ITEMS.register("wooden_automatic_bow",
            () -> new WoodenAutomaticBowItem(new Item.Properties().tab(ModItemTab.instance).durability(ItemTier.WOOD.getUses()), 60));

    public static final RegistryObject<Item> STONE_AUTOMATIC_BOW = ITEMS.register("stone_automatic_bow",
            () -> new StoneAutomaticBowItem(new Item.Properties().tab(ModItemTab.instance).durability(ItemTier.STONE.getUses()), 50));

    public static final RegistryObject<Item> GOlDEN_AUTOMATIC_BOW = ITEMS.register("golden_automatic_bow",
            () -> new GoldenAutomaticBowItem(new Item.Properties().tab(ModItemTab.instance).durability(ItemTier.GOLD.getUses()), 60));

    public static final RegistryObject<Item> IRON_AUTOMATIC_BOW = ITEMS.register("iron_automatic_bow",
            () -> new IronAutomaticBowItem(new Item.Properties().tab(ModItemTab.instance).durability(ItemTier.IRON.getUses()), 40));

    public static final RegistryObject<Item> DIAMOND_AUTOMATIC_BOW = ITEMS.register("diamond_automatic_bow",
            () -> new DiamondAutomaticBowItem(new Item.Properties().tab(ModItemTab.instance).durability(ItemTier.DIAMOND.getUses()), 30));

    public static final RegistryObject<Item> NETHERITE_AUTOMATIC_BOW = ITEMS.register("netherite_automatic_bow",
            () -> new NetheriteAutomaticBowItem(new Item.Properties().tab(ModItemTab.instance).durability(ItemTier.NETHERITE.getUses()), 20));

    public static final RegistryObject<Item> BONE_FLUTE = ITEMS.register("bone_flute",
            () -> new BoneFluteItem(new Item.Properties().tab(ModItemTab.instance).durability(200)));

    public static final RegistryObject<Item> ANCIENT_SWORD = ITEMS.register("ancient_sword",
            () -> new AncientSwordItem(new Item.Properties().tab(ModItemTab.instance).durability(1000).fireResistant().setISTER(() -> AncientSwordRenderer::new)));

    public static final RegistryObject<Item> GLADIATOR_SKELETON_SPAWN_EGG = ITEMS.register("gladiator_skeleton_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.GLADIATOR_SKELETON, 0xc4c4c4, 0x9da4a8, (new Item.Properties()).tab(ModItemTab.instance)));

    public static final RegistryObject<Item> ANCIENT_SKELETON_SPAWN_EGG = ITEMS.register("ancient_skeleton_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.ANCIENT_SKELETON, 0xc4c4c4, 0xebce2a, (new Item.Properties()).tab(ModItemTab.instance)));

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

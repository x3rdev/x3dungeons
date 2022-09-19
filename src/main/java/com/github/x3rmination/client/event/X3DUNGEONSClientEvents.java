package com.github.x3rmination.client.event;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonRenderer;
import com.github.x3rmination.common.entities.CasterPiglin.CasterPiglinRenderer;
import com.github.x3rmination.common.entities.GiantPiglin.GiantPiglinRenderer;
import com.github.x3rmination.common.entities.GladiatorSkeleton.GladiatorSkeletonRenderer;
import com.github.x3rmination.common.entities.Spear.SpearRenderer;
import com.github.x3rmination.common.entities.SweepProjectile.SweepProjectileRenderer;
import com.github.x3rmination.common.items.AutomaticBow.AutomaticBowItem;
import com.github.x3rmination.common.items.BoneFlute.BoneFluteItem;
import com.github.x3rmination.common.items.GoldenShield.GoldOverlay;
import com.github.x3rmination.common.items.GoldenShield.GoldenShield;
import com.github.x3rmination.common.items.SpearItem;
import com.github.x3rmination.core.registry.EntityInit;
import com.github.x3rmination.core.registry.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class X3DUNGEONSClientEvents {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SPEAR.get(), SpearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SWEEP_PROJECTILE.get(), SweepProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GLADIATOR_SKELETON.get(), GladiatorSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ANCIENT_SKELETON.get(), AncientSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CASTER_PIGLIN.get(), CasterPiglinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.GIANT_PIGLIN.get(), GiantPiglinRenderer::new);
        for (Map.Entry<String, PlayerRenderer> entry : Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().entrySet()) {
            PlayerRenderer render = entry.getValue();
            render.addLayer(new GoldOverlay(render));
        }
        event.enqueueWork(() -> {
            for(RegistryObject<Item> registryObject : ItemInit.ITEMS.getEntries()) {
                if(registryObject.get() instanceof SpearItem) {
                    ItemModelsProperties.register(registryObject.get(), new ResourceLocation(X3DUNGEONS.MOD_ID, "throwing"),
                            (stack, world, entity) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
                }
                if(registryObject.get() instanceof AutomaticBowItem) {
                    ItemModelsProperties.register(registryObject.get(), new ResourceLocation(X3DUNGEONS.MOD_ID, "pull"), (stack, world, livingEntity) -> {
                        if (livingEntity == null) {
                            return 0.0F;
                        } else {
                            AutomaticBowItem item = ((AutomaticBowItem) stack.getItem());
                            return livingEntity.getUseItem() != stack ? 0.0F : (((float) item.getProgress())/ ((float) item.getThreshold()));
                        }
                    });
                }
                if(registryObject.get() instanceof AutomaticBowItem || registryObject.get() instanceof BoneFluteItem) {
                    ItemModelsProperties.register(registryObject.get(), new ResourceLocation(X3DUNGEONS.MOD_ID, "pulling"), (stack, world, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);
                }
                if(registryObject.get() instanceof GoldenShield) {
                    ItemModelsProperties.register(registryObject.get(), new ResourceLocation(X3DUNGEONS.MOD_ID, "blocking"), (stack, world, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);
                }
            }
        });
    }
}

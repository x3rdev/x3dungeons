package com.github.x3rmination.core.event;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonEntity;
import com.github.x3rmination.common.entities.GladiatorSkeleton.GladiatorSkeletonEntity;
import com.github.x3rmination.common.items.BoneFlute.BoneFluteItem;
import com.github.x3rmination.core.registry.EntityInit;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.GLADIATOR_SKELETON.get(), GladiatorSkeletonEntity.setCustomAttributes().build());
        event.put(EntityInit.ANCIENT_SKELETON.get(), AncientSkeletonEntity.setCustomAttributes().build());
    }

    @SubscribeEvent
    public void renderBoneFlute(RenderLivingEvent event) {
        if(event.getEntity() instanceof ClientPlayerEntity) {
            ClientPlayerEntity player = (ClientPlayerEntity) event.getEntity();
            System.out.println(player.isUsingItem());
            if(player.isUsingItem() && event.getRenderer().getModel() instanceof PlayerModel) {
                PlayerModel model = (PlayerModel) event.getRenderer().getModel();
                model.body.visible = false;
            }
        }
    }
}

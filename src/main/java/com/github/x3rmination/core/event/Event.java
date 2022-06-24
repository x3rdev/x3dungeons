package com.github.x3rmination.core.event;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.SkeletonGladiator.SkeletonGladiatorEntity;
import com.github.x3rmination.core.registry.EntityInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Event {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.SKELETON_GLADIATOR.get(), SkeletonGladiatorEntity.setCustomAttributes().build());
    }

}

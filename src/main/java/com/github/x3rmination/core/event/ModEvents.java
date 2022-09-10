package com.github.x3rmination.core.event;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonEntity;
import com.github.x3rmination.common.entities.CasterPiglin.CasterPiglinEntity;
import com.github.x3rmination.common.entities.GiantPiglin.GiantPiglinEntity;
import com.github.x3rmination.common.entities.GladiatorSkeleton.GladiatorSkeletonEntity;
import com.github.x3rmination.core.registry.EntityInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.GLADIATOR_SKELETON.get(), GladiatorSkeletonEntity.setCustomAttributes().build());
        event.put(EntityInit.ANCIENT_SKELETON.get(), AncientSkeletonEntity.setCustomAttributes().build());
        event.put(EntityInit.CASTER_PIGLIN.get(), CasterPiglinEntity.setCustomAttributes().build());
        event.put(EntityInit.GIANT_PIGLIN.get(), GiantPiglinEntity.setCustomAttributes().build());
    }
}

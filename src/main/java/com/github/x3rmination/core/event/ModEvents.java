package com.github.x3rmination.core.event;

import com.github.x3rmination.X3DUNGEONS;
import com.github.x3rmination.common.entities.AncientSkeleton.AncientSkeletonEntity;
import com.github.x3rmination.common.entities.CannonPiglin.CannonPiglinEntity;
import com.github.x3rmination.common.entities.Floppa.FloppaEntity;
import com.github.x3rmination.common.entities.GladiatorSkeleton.GladiatorSkeletonEntity;
import com.github.x3rmination.common.entities.LeanZombie.LeanZombieEntity;
import com.github.x3rmination.common.items.BoneFlute.BoneFluteItem;
import com.github.x3rmination.core.registry.EntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = X3DUNGEONS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.GLADIATOR_SKELETON.get(), GladiatorSkeletonEntity.setCustomAttributes().build());
        event.put(EntityInit.ANCIENT_SKELETON.get(), AncientSkeletonEntity.setCustomAttributes().build());
        event.put(EntityInit.CANNON_PIGLIN.get(), CannonPiglinEntity.setCustomAttributes().build());
        event.put(EntityInit.LEAN_ZOMBIE.get(), LeanZombieEntity.setCustomAttributes().build());
        event.put(EntityInit.FLOPPA.get(), FloppaEntity.setCustomAttributes().build());
    }
}

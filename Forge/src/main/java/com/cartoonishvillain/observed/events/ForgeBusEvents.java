package com.cartoonishvillain.observed.events;

import com.cartoonishvillain.observed.Constants;
import com.cartoonishvillain.observed.capabilities.PlayerCapabilityManager;
import com.cartoonishvillain.observed.commands.SetObservedLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents {

    @SubscribeEvent
    public static void commands(RegisterCommandsEvent event){
        SetObservedLevel.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void playerRegister(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player) {
            PlayerCapabilityManager provider = new PlayerCapabilityManager();
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "observationlevels"), provider);
        }
    }
}

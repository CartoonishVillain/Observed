package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.entity.ObserverEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Register {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Constants.MOD_ID);


    public static void init(){
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUND_EVENT.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> OBSERVEREYE = ITEMS.register("observereye", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(1f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 20*90, 0), 1).meat().build())));

    public static final RegistryObject<EntityType<ObserverEntity>> OBSERVER = ENTITY_TYPES.register("observer", () -> EntityType.Builder.of(ObserverEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build(new ResourceLocation(Constants.MOD_ID, "observer").toString()));

    public static final RegistryObject<SoundEvent> OBSERVERATTACK = SOUND_EVENT.register("attack_sounds", () -> new SoundEvent(new ResourceLocation(Constants.MOD_ID, "attack_sounds")));
    public static final RegistryObject<SoundEvent> OBSERVERHURT = SOUND_EVENT.register("hurt_sounds", () -> new SoundEvent(new ResourceLocation(Constants.MOD_ID, "hurt_sounds")));
    public static final RegistryObject<SoundEvent> OBSERVERDEATH = SOUND_EVENT.register("death_sounds", () -> new SoundEvent(new ResourceLocation(Constants.MOD_ID, "death_sounds")));


    public static final RegistryObject<Item> OBSERVERSPAWN = ITEMS.register("observer_spawn_egg", () -> new ObserverSpawnEgg(Register.OBSERVER, 2097152, 10131200, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> LENS = ITEMS.register("observer_lenses", () -> new Lenses(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)));


}

package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.armor.LensArmor;
import com.cartoonishvillain.observed.armor.ModArmor;
import com.cartoonishvillain.observed.entity.ObserverEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Register {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Observed.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Observed.MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Observed.MODID);


    public static void init(){
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUND_EVENT.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> OBSERVEREYE = ITEMS.register("observereye", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_FOOD).food(new Food.Builder().nutrition(2).saturationMod(1f).effect(() -> new EffectInstance(Effects.NIGHT_VISION, 20*90, 0), 1).meat().build())));

    public static final RegistryObject<EntityType<ObserverEntity>> OBSERVER = ENTITY_TYPES.register("observer", () -> EntityType.Builder.of(ObserverEntity::new, EntityClassification.MONSTER).sized(0.6f, 1.95f).build(new ResourceLocation(Observed.MODID, "observer").toString()));

    public static final RegistryObject<SoundEvent> OBSERVERATTACK = SOUND_EVENT.register("attack_sounds", () -> new SoundEvent(new ResourceLocation(Observed.MODID, "attack_sounds")));
    public static final RegistryObject<SoundEvent> OBSERVERHURT = SOUND_EVENT.register("hurt_sounds", () -> new SoundEvent(new ResourceLocation(Observed.MODID, "hurt_sounds")));
    public static final RegistryObject<SoundEvent> OBSERVERDEATH = SOUND_EVENT.register("death_sounds", () -> new SoundEvent(new ResourceLocation(Observed.MODID, "death_sounds")));

    public static final RegistryObject<Item> OBSERVERSPAWN = ITEMS.register("observer_spawn_egg", () -> new ObserverSpawnEgg(Register.OBSERVER, 2097152, 10131200, new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<ArmorItem> LENSARMOR = ITEMS.register("observer_lenses", () -> new LensArmor(ModArmor.LENS, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

}

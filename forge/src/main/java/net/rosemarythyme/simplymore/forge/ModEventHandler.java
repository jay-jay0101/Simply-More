package net.rosemarythyme.simplymore.forge;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.SimplyMoreClientInit;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.uniques.BrassturnItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = SimplyMore.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(SimplyMoreClientInit::registerModelPredicates);
    }

    @SubscribeEvent
    public static void onRegisterItems(RegisterEvent event) {
        if (event.getRegistryKey() == RegistryKeys.ITEM) {
            ModItemsRegistry.registerItemGroup();
        }
    }


}
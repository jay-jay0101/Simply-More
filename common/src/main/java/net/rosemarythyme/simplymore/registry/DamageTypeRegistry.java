package net.rosemarythyme.simplymore.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;

public class DamageTypeRegistry {
    public static final RegistryKey<DamageType> LIGHTNING =
            RegistryKey.of(
                    RegistryKeys.DAMAGE_TYPE,
                    SimplyMore.identifier("lightning")
            );

    public static DamageSource damageSourceOf(World world, RegistryKey<DamageType> type) {
        return damageSourceOf(world, type, null);
    }

    public static DamageSource damageSourceOf(World world, RegistryKey<DamageType> type, LivingEntity source) {
        RegistryEntry<DamageType> entry = world.getRegistryManager()
                .getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE)
                .getOrThrow(type);

        return source == null ?
                new DamageSource(entry) :
                new DamageSource(entry, source);
    }
}

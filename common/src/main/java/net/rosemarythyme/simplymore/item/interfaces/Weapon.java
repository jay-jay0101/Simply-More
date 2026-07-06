package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;

public interface Weapon {

    SwordType getSwordType();

    static void tryGrantLanceEffect(LivingEntity attacker, LivingEntity target) {
        if (EntityUtils.shouldGrantLanceEffect(attacker)) {
            DamageSource source = attacker instanceof PlayerEntity player ?
                    attacker.getDamageSources().playerAttack(player) :
                    attacker.getDamageSources().mobAttack(attacker);

            AttackUtils.applyExtraDamage(target,
                    ConfigWrapper.attributes.weaponTypesDamage.lancefriendship_damage_modifier,
                    source
            );
        }
    }

    enum SwordType {
        SWORD,
        LANCE,
        GRANDSWORD
    }
}

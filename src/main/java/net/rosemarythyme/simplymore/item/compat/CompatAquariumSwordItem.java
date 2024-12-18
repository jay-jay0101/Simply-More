package net.rosemarythyme.simplymore.item.compat;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import nourl.mythicmetals.abilities.Abilities;
import nourl.mythicmetals.abilities.Ability;
import nourl.mythicmetals.misc.UsefulSingletonForColorUtil;

public class CompatAquariumSwordItem extends CompatSwordItem {

    public CompatAquariumSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, boolean grandsword, boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, grandsword, lance, repairIngredient);
        Abilities.AQUA_AFFINITY.addItem(this, UsefulSingletonForColorUtil.MetalColors.AQUA_STYLE);
    }
}

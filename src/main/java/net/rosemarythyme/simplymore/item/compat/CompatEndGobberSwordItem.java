package net.rosemarythyme.simplymore.item.compat;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.WrapperConfig;

public class CompatEndGobberSwordItem extends CompatSwordItem {

    public CompatEndGobberSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, boolean grandsword, boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, grandsword, lance, repairIngredient);
    }

    static boolean unbreakable = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig().weaponAttributes.isGobberEndWeaponUnbreakable();

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player)
    {
        if(world.isClient) return;

        if(unbreakable)
        {
            stack.getOrCreateNbt().putBoolean("Unbreakable", true);
        }
    }
}

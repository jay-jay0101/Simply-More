package net.rosemarythyme.simplymore.world.abilities;

import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

public class StatueAbilityType extends PetrifiedAbilityType {
    public StatueAbilityType() {
        super();
    }

    @Override
    public void onFinish(ActiveAbilityManager.ActiveAbility ability) {
        EntityUtils.cooldown(ability.owner(), ItemRegistry.BLADE_OF_THE_GROTESQUE.get(), BladeOfTheGrotesqueItem.SETTINGS.cooldown, true);
        super.onFinish(ability);
    }
}

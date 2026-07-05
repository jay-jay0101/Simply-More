package net.rosemarythyme.simplymore.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedCondition;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;

public class WeaponAttributesConfig extends Config {
    public WeaponAttributesConfig() {
        super(SimplyMore.identifier("weapon_attributes"));
    }

    @RequiresAction(action = Action.RESTART)
    public WeaponTypesDamage weaponTypesDamage = new WeaponTypesDamage();
    @RequiresAction(action = Action.RESTART)
    public WeaponTiersModifiers typeDamageModifier = new WeaponTiersModifiers();
    @RequiresAction(action = Action.RESTART)
    public WeaponTypesSwingSpeed weaponTypesSwingSpeed = new WeaponTypesSwingSpeed();
    @RequiresAction(action = Action.RESTART)
    public UniqueWeaponsDamage uniqueWeaponsDamage = new UniqueWeaponsDamage();
    @RequiresAction(action = Action.RESTART)
    public UniqueWeaponsSwingSpeed uniqueWeaponsSwingSpeed = new UniqueWeaponsSwingSpeed();

    @RequiresAction(action = Action.RESTART)
    public static class WeaponTiersModifiers extends ConfigSection {
        // Stick n' Stone compat
        public ValidatedCondition<Float> wooden_damage_modifier = ConfigWrapper.modLoadedCondition(3.0f, "sticknstone");
        public ValidatedCondition<Float> stone_damage_modifier = ConfigWrapper.modLoadedCondition(3.0f, "sticknstone");
        // Mythic Metals compat
        public ValidatedCondition<Float> hallowed_damage_modifier = ConfigWrapper.modLoadedCondition(3.0f, "mythicmetals");
        public ValidatedCondition<Float> legendary_banglum_damage_modifier = ConfigWrapper.modLoadedCondition(3.0f, "mythicmetals");
        public ValidatedCondition<Float> tidesinger_damage_modifier = ConfigWrapper.modLoadedCondition(3.0f, "mythicmetals");
    }

    @RequiresAction(action = Action.RESTART)
    public static class WeaponTypesDamage extends ConfigSection {
        public int greatkatana_damage_modifier = 1;
        public int grandsword_damage_modifier = 6;
        public int backhandblade_damage_modifier = -2;
        public int lance_damage_modifier = 0;
        public int lancefriendship_damage_modifier = 6;
        public int khopesh_damage_modifier = -1;
        public int dagger_damage_modifier = -2;
        public int quarterstaff_damage_modifier = -2;
        public int pernach_damage_modifier = 1;
        public int greatspear_damage_modifier = 3;
        public int deerhorns_damage_modifier = -1;
    }

    @RequiresAction(action = Action.RESTART)
    public static class WeaponTypesSwingSpeed extends ConfigSection {
        public float greatkatana_attack_speed = -2.6f;
        public float grandsword_attack_speed = -3.4f;
        public float backhandblade_attack_speed = -1.7f;
        public float lance_attack_speed = -3.0f;
        public float khopesh_attack_speed = -2.1f;
        public float dagger_attack_speed = -1.8f;
        public float quarterstaff_attack_speed = -2.0f;
        public float pernach_attack_speed = -2.7f;
        public float greatspear_attack_speed = -3.0f;
        public float deerhorns_attack_speed = -1.9f;
    }

    @RequiresAction(action = Action.RESTART)
    public static class UniqueWeaponsDamage extends ConfigSection {
        public int greatslither_damage_modifier = 5;
        public int moltenflare_damage_modifier = 10;
        public int grandfrost_damage_modifier = 11;
        public int glimmerstep_damage_modifier = 3;
        public int thebloodharvester_damage_modifier = 2;
        public int jesterpenetrate_damage_modifier = 0;
        public int mimicry_damage_modifier = 4;
        public int myrmedge_damage_modifier = 2;
        public int blackpearl_damage_modifier = 3;
        public int thepan_damage_modifier = -2;
        public int thevesselbreach_damage_modifier = 0;
        public int bladeofthegrotesque_damage_modifier = 6;
        public int viperscall_damage_modifier = 0;
        public int timekeeper_damage_modifier = 2;
        public int matterbane_damage_modifier = 3;
        public int smoulderingruin_damage_modifier = 1;
        public int stasis_damage_modifier = 2;
        public int tidebreaker_damage_modifier = 3;
        public int ruyijingubang_damage_modifier = 3;
        public int rupturedidol_damage_modifier = 4;
        public int ascendedidol_damage_modifier = 4;
        public int tarnishedidol_damage_modifier = 4;
        public int holylight_damage_modifier = 4;
        public int darksent_damage_modifier = 4;
        public int boasfang_damage_modifier = 1;
        public int earthshatter_damage_modifier = 6;
        public int soulforeseer_damage_modifier = 3;
        public int serpentinevalour_damage_modifier = 5;
        public int lustrousmoxie_damage_modifier = 4;
        public int brassturn_damage_modifier = 2;
        public int cindergorge_damage_modifier = 5;
        public int deathseyrie_damage_modifier = 6;
        public int perforiscus_damage_modifier = 4;
        public int revvengine_damage_modifier = 2;
        public int exedrill_damage_modifier = 3;
        public int culterex_damage_modifier = 2;
    }

    @RequiresAction(action = Action.RESTART)
    public static class UniqueWeaponsSwingSpeed extends ConfigSection {
        public float greatslither_attack_speed = -2.6f;
        public float moltenflare_attack_speed = -3.4f;
        public float grandfrost_attack_speed = -3.4f;
        public float glimmerstep_attack_speed = -3.0f;
        public float thebloodharvester_attack_speed = -2.4f;
        public float jesterpenetrate_attack_speed = -3.0f;
        public float myrmedge_attack_speed = -2.1f;
        public float blackpearl_attack_speed = -2.0f;
        public float thepan_attack_speed = -2.5f;
        public float thevesselbreach_attack_speed = -1.8f;
        public float bladeofthegrotesque_attack_speed = -2.8f;
        public float viperscall_attack_speed = -3.0f;
        public float timekeeper_attack_speed = -2.0f;
        public float matterbane_attack_speed = -2.4f;
        public float smoulderingruin_attack_speed = -1.7f;
        public float stasis_attack_speed = -2.0f;
        public float tidebreaker_attack_speed = -1.9f;
        public float ruyijingubang_attack_speed = -2.0f;
        public float rupturedidol_attack_speed = -2.7f;
        public float ascendedidol_attack_speed = -2.7f;
        public float tarnishedidol_attack_speed = -2.7f;
        public float holylight_attack_speed = -2.7f;
        public float darksent_attack_speed = -2.7f;
        public float boasfang_attack_speed = -2.2f;
        public float earthshatter_attack_speed = -3.4f;
        public float soulforeseer_attack_speed = -2.6f;
        public float serpentinevalour_attack_speed = -3.3f;
        public float lustrousmoxie_attack_speed = -2.6f;
        public float brassturn_attack_speed = -0.2f;
        public float cindergorge_attack_speed = -2.7f;
        public float deathseyrie_attack_speed = -3.2f;
        public float perforiscus_attack_speed = -3.3f;
        public float revvengine_attack_speed = -2.0f;
        public float exedrill_attack_speed = -3.0f;
        public float culterex_attack_speed = -1.8f;
    }
}
package net.rosemarythyme.simplymore.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.*;
import net.rosemarythyme.simplymore.item.uniques.idols.DarksentItem;
import net.rosemarythyme.simplymore.item.uniques.idols.HolylightItem;
import net.rosemarythyme.simplymore.item.uniques.joke.ThePanItem;
import net.rosemarythyme.simplymore.util.ConfigUtils;

public class UniqueEffectConfig extends Config {

    public UniqueEffectConfig() {
        super(SimplyMore.identifier("unique_effect"));
    }

    public ValidatedSet<Identifier> globalBlacklist = ConfigUtils.createEffectList(
            Identifier.of("simplyswords:magislam"),
            Identifier.of("simplyswords:fatal_flicker"),
            SimplyMore.identifier("grasping"),
            SimplyMore.identifier("mimicry_happening"),
            SimplyMore.identifier("mistified"),
            SimplyMore.identifier("rage")
    );

    public BlackPearlItem.EffectSettings black_pearl = new BlackPearlItem.EffectSettings();
    public BladeOfTheGrotesqueItem.EffectSettings blade_of_the_grotesque = new BladeOfTheGrotesqueItem.EffectSettings();
    public BoasFangItem.EffectSettings boas_fang = new BoasFangItem.EffectSettings();
    public EarthshatterItem.EffectSettings earthshatter = new EarthshatterItem.EffectSettings();
    public GlimmerstepItem.EffectSettings glimmerstep = new GlimmerstepItem.EffectSettings();
    public GrandfrostItem.EffectSettings grandfrost = new GrandfrostItem.EffectSettings();
    public GreatSlitherItem.EffectSettings great_slither = new GreatSlitherItem.EffectSettings();
    public LustrousMoxieItem.EffectSettings lustrous_moxie = new LustrousMoxieItem.EffectSettings();
    public MatterbaneItem.EffectSettings matterbane = new MatterbaneItem.EffectSettings();
    public MimicryItem.EffectSettings mimicry = new MimicryItem.EffectSettings();
    public MoltenFlareItem.EffectSettings molten_flare = new MoltenFlareItem.EffectSettings();
    public RuyiJinguBangItem.EffectSettings ruyi_jingu_bang = new RuyiJinguBangItem.EffectSettings();
    public MyrmedgeItem.EffectSettings myrmedge = new MyrmedgeItem.EffectSettings();
    public SerpentineValourItem.EffectSettings serpentine_valour = new SerpentineValourItem.EffectSettings();
    public ThePanItem.EffectSettings the_pan = new ThePanItem.EffectSettings();
    public HolylightItem.EffectSettings holylight = new HolylightItem.EffectSettings();
    public DarksentItem.EffectSettings darksent = new DarksentItem.EffectSettings();
    public SmoulderingRuinItem.EffectSettings smouldering_ruin = new SmoulderingRuinItem.EffectSettings();
    public SoulForeseerItem.EffectSettings soul_foreseer = new SoulForeseerItem.EffectSettings();
    public StasisItem.EffectSettings stasis = new StasisItem.EffectSettings();
    public TheBloodHarvesterItem.EffectSettings the_blood_harvester = new TheBloodHarvesterItem.EffectSettings();
    public TheVesselBreachItem.EffectSettings the_vessel_breach = new TheVesselBreachItem.EffectSettings();
    public TidebreakerItem.EffectSettings tidebreaker = new TidebreakerItem.EffectSettings();
    public TimekeeperItem.EffectSettings timekeeper = new TimekeeperItem.EffectSettings();
    public VipersCallItem.EffectSettings vipers_call = new VipersCallItem.EffectSettings();
    public BrassturnItem.EffectSettings brassturn = new BrassturnItem.EffectSettings();
    public CindergorgeItem.EffectSettings cindergorge = new CindergorgeItem.EffectSettings();
    public DeathsEyrieItem.EffectSettings deaths_eyrie = new DeathsEyrieItem.EffectSettings();
    public PerforiscusItem.EffectSettings perforiscus = new PerforiscusItem.EffectSettings();
    public RevvengineItem.EffectSettings revvengine = new RevvengineItem.EffectSettings();
    public ExedrillItem.EffectSettings exedrill = new ExedrillItem.EffectSettings();
    public CulterexItem.EffectSettings culterex = new CulterexItem.EffectSettings();
}
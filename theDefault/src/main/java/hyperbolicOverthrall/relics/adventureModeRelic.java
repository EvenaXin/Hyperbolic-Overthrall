package hyperbolicOverthrall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.patches.RelicTags;
import hyperbolicOverthrall.util.TextureLoader;

import java.util.ArrayList;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeRelicOutlinePath;
import static hyperbolicOverthrall.HyperbolicOverthrall.makeRelicPath;

public class adventureModeRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = HyperbolicOverthrall.makeID("adventureModeRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public adventureModeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        ArrayList<String> Tags = new ArrayList<String>();
        Tags.add("RUNIC");
        Tags.add("ICECREAM");
        RelicTags.relicTags.put(ID, Tags);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new GainEnergyAction(4 + (AbstractDungeon.player.energy.energyMaster - 1) * 3));
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new DrawCardAction(AbstractDungeon.player, 4 + (AbstractDungeon.player.masterHandSize - 1) * 3));
    }

    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster = AbstractDungeon.player.energy.energyMaster - 2;
        AbstractDungeon.player.masterHandSize = AbstractDungeon.player.masterHandSize - 4;
    }
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster = AbstractDungeon.player.energy.energyMaster + 2;
        AbstractDungeon.player.masterHandSize = AbstractDungeon.player.masterHandSize + 4;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

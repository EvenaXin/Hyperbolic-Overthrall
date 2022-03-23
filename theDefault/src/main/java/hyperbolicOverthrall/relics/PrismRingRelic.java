package hyperbolicOverthrall.relics;

import basemod.abstracts.CustomRelic;
import chronoMods.TogetherManager;
import chronoMods.network.RemotePlayer;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnReceivePowerPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.unique.ApplyBulletTimeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.util.TextureLoader;

import java.util.ArrayList;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeRelicOutlinePath;
import static hyperbolicOverthrall.HyperbolicOverthrall.makeRelicPath;

public class PrismRingRelic extends CustomRelic {

    public static final String ID = HyperbolicOverthrall.makeID("PrismRingRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BottledPlaceholder.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BottledPlaceholder.png"));

    public PrismRingRelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, IMG, tier, sfx);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.img = texture;
        this.outlineImg = texture;
    }

    public PrismRingRelic() {
        super(ID, IMG, RelicTier.STARTER, LandingSound.FLAT);

    }

    @Override
    public void atTurnStart() {
        ArrayList<AbstractCard.CardColor> colors = new ArrayList<>();
        LoseStrengthPower neutralLoseStrength = new LoseStrengthPower(AbstractDungeon.player, 0);
        neutralLoseStrength.type = NeutralPowertypePatch.NEUTRAL;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group.toArray(new AbstractCard[0])) {
            if (!colors.contains(c.color)) {
                colors.add(c.color);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group.toArray(new AbstractCard[0])) {
            if (!colors.contains(c.color)) {
                colors.add(c.color);
            }
        }
        neutralLoseStrength.amount = colors.size();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, colors.size())));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, neutralLoseStrength));
    }

    @Override
    public void onPlayerEndTurn() {
        ArrayList<AbstractCard.CardColor> colors = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.drawPile.group.toArray(new AbstractCard[0])) {
            if (!colors.contains(c.color)) {
                colors.add(c.color);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group.toArray(new AbstractCard[0])) {
            if (!colors.contains(c.color)) {
                colors.add(c.color);
            }
        }
        this.addToBot(new GainBlockAction(AbstractDungeon.player, colors.size()));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PrismRingRelic();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

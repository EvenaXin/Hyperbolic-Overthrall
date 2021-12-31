package hyperbolicOverthrall.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.characters.SunWukong;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;

public class SunWukongEnergizingAle extends AbstractDynamicCard {
    public static final String ID = HyperbolicOverthrall.makeID(SunWukongEnergizingAle.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = SunWukong.Enums.COLOR_YELLOW;

    private static final int COST = -1;

    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;

    public SunWukongEnergizingAle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = baseMagicNumber;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DiscardAction(p, p, 1, false));
        this.addToBot(new LoseEnergyAction(this.energyOnUse));
        this.addToBot(new GainEnergyAction(this.energyOnUse));
        if (this.energyOnUse >= 2) {
            this.addToBot(new GainEnergyAction(2));
        }
        else {
            this.addToBot(new GainEnergyAction(this.energyOnUse));
        }
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
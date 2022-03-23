package hyperbolicOverthrall.cards.executiveCards;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.actions.LoseGoldAction;
import hyperbolicOverthrall.cards.AbstractDynamicCard;
import hyperbolicOverthrall.cards.PostBattleCard;

import java.util.Iterator;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;
import static hyperbolicOverthrall.characters.Executive.Enums.COLOR_CONCRETE_GRAY;

public class executiveTightInvestment extends AbstractDynamicCard implements PostBattleCard {
    public static final String ID = HyperbolicOverthrall.makeID(executiveTightInvestment.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static CardStrings cardStrings;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_CONCRETE_GRAY;

    private static final int COST = 1;

    private static final int VALUE = 60;

    private static final int MAGIC_NUMBER = 20;

    public executiveTightInvestment() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC_NUMBER;
        this.misc = VALUE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseGoldAction(20));
        this.misc += 20;
    }

    @Override
    public void postBattleEffect() {
        this.misc += Math.round(this.misc * 0.1);
    }

    @Override
    public void onRemoveFromMasterDeck() {
        this.addToBot(new GainGoldAction(this.misc));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (AbstractDungeon.player.gold <= 20) {
                canUse = false;
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }
        }
        return canUse;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
        else {
            upgradeMagicNumber(5);
        }
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
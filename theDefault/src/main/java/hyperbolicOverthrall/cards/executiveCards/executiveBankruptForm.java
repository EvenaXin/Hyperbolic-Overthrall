package hyperbolicOverthrall.cards.executiveCards;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.actions.LoseGoldAction;
import hyperbolicOverthrall.cards.AbstractDynamicCard;
import hyperbolicOverthrall.cards.PostBattleCard;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;
import static hyperbolicOverthrall.characters.Executive.Enums.COLOR_CONCRETE_GRAY;

public class executiveBankruptForm extends AbstractDynamicCard {
    public static final String ID = HyperbolicOverthrall.makeID(executiveBankruptForm.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    public static CardStrings cardStrings;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = COLOR_CONCRETE_GRAY;

    private static final int COST = 3;

    private static final int MAGIC_NUMBER = 50;
    private static final int UPGRADE_MAGIC_NUMBER = 30;

    public executiveBankruptForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseGoldAction(AbstractDungeon.player.gold));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
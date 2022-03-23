package hyperbolicOverthrall.cards.cognizantCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.cards.AbstractDynamicCard;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;
import static hyperbolicOverthrall.characters.Cognizant.Enums.COLOR_FIBRE_GREEN;


public class cognizantGatherThoughts extends AbstractDynamicCard {
    public static final String ID = HyperbolicOverthrall.makeID(cognizantGatherThoughts.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = COLOR_FIBRE_GREEN;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 6;
    private static final int UPGRADE_MAGIC_NUMBER = 3;

    public cognizantGatherThoughts() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void triggerOnManualDiscard() {

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
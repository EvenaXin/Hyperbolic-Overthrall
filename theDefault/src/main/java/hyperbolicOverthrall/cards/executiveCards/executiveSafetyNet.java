package hyperbolicOverthrall.cards.executiveCards;

import chronoMods.network.RemotePlayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.cards.CourierCard;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;
import static hyperbolicOverthrall.characters.Altruist.Enums.COLOR_SKY_BLUE;
import static hyperbolicOverthrall.characters.Executive.Enums.COLOR_CONCRETE_GRAY;


public class executiveSafetyNet extends CourierCard {
    public static final String ID = HyperbolicOverthrall.makeID(executiveSafetyNet.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_CONCRETE_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public boolean doubled = false;

    public executiveSafetyNet() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.doubled) {
            this.addToBot(new GainBlockAction(p, p, 2*block));
        }
        else {
            this.addToBot(new GainBlockAction(p, p, block));
        }
    }

    @Override
    public void onSendCard(RemotePlayer player) {
        this.doubled = true;
        this.timesSent += 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
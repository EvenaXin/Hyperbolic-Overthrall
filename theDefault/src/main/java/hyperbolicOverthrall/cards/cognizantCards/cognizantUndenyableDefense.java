package hyperbolicOverthrall.cards.cognizantCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.cards.AbstractDynamicCard;

import java.util.ArrayList;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;
import static hyperbolicOverthrall.characters.Cognizant.Enums.COLOR_FIBRE_GREEN;


public class cognizantUndenyableDefense extends AbstractDynamicCard {
    public static final String ID = HyperbolicOverthrall.makeID(cognizantUndenyableDefense.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_FIBRE_GREEN;

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    public cognizantUndenyableDefense() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<CardColor> colors = new ArrayList<>();
        for (AbstractCard c : p.hand.group) {
            if (!colors.contains(c.color)) {
                colors.add(c.color);
            }
        }
        if (!colors.contains(this.color)) {
            colors.add(this.color);
        }
        for (CardColor color : colors) {
            this.addToBot(new GainBlockAction(p, this.block));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
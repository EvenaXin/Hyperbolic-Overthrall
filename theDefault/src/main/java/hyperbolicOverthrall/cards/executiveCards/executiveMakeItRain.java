package hyperbolicOverthrall.cards.executiveCards;

import basemod.interfaces.XCostModifier;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.cards.AbstractDynamicCard;

import java.util.ArrayList;
import java.util.List;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;
import static hyperbolicOverthrall.characters.Executive.Enums.COLOR_CONCRETE_GRAY;

public class executiveMakeItRain extends AbstractDynamicCard {
    public static final String ID = HyperbolicOverthrall.makeID(executiveMakeItRain.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = COLOR_CONCRETE_GRAY;

    private static final int COST = -1;

    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_MAGIC_NUMBER = 3;

    public executiveMakeItRain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = energyOnUse;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)) {
            i += ChemicalX.BOOST;
        }
        this.addToBot(new GainGoldAction(magicNumber * i));
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
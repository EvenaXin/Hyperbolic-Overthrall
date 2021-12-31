package hyperbolicOverthrall.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.characters.SunWukong;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeCardPath;

public class SunWukongPreparedMaterials extends AbstractDynamicCard implements StartupCard {
    public static final String ID = HyperbolicOverthrall.makeID(SunWukongPreparedMaterials.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = SunWukong.Enums.COLOR_YELLOW;

    private static final int COST = -2;

    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;

    public SunWukongPreparedMaterials() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC_NUMBER;
        this.magicNumber = baseMagicNumber;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(p, this.magicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = "Nope, sorry.";
        return false;
    }

    public void triggerOnManualDiscard() {
        this.addToBot(new GainEnergyAction(1));
    }

    @Override
    public boolean atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        return true;
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
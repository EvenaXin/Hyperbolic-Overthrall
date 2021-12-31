package hyperbolicOverthrall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

@SpirePatch(
        clz = EnergyManager.class,
        method = "recharge"
)

public class SunWukongBaseRelicPatch2 {

    @SpirePrefixPatch
    public static SpireReturn Prefix(EnergyManager __Instance) {
        if (PatchMethods.getIceCreamRelics()) {
            if (EnergyPanel.totalCount > 0) {
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, new IceCream()));
            }

            EnergyPanel.addEnergy(1);

            AbstractDungeon.actionManager.updateEnergyGain(__Instance.energy); // the code below the if statements

            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
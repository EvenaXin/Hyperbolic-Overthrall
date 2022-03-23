package hyperbolicOverthrall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import hyperbolicOverthrall.orbs.scrapPackage.AbstractScrap;

import java.util.ArrayList;

@SpirePatch(
        clz= GameActionManager.class,
        method= SpirePatch.CLASS
)
public class ActionManagerScrapPatch {
    public static SpireField<ArrayList<AbstractScrap>> scrapScavengedThisCombat = new SpireField<ArrayList<AbstractScrap>>(() -> new ArrayList<AbstractScrap>());
    public static SpireField<ArrayList<AbstractScrap>> scrapScavengedThisTurn = new SpireField<ArrayList<AbstractScrap>>(() -> new ArrayList<AbstractScrap>());
}

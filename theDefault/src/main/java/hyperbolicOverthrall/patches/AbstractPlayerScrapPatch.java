package hyperbolicOverthrall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import hyperbolicOverthrall.orbs.scrapPackage.AbstractScrap;

import java.util.ArrayList;

@SpirePatch(
        clz= AbstractPlayer.class,
        method= SpirePatch.CLASS
)
public class AbstractPlayerScrapPatch {
    public static SpireField<ArrayList<AbstractScrap>> scrap = new SpireField<ArrayList<AbstractScrap>>(() -> new ArrayList<AbstractScrap>());
    public static SpireField<Integer> maxScrap = new SpireField<Integer>(() -> 3);
}

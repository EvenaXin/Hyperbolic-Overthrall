package hyperbolicOverthrall.patches.cardMethodPatches;

import chronoMods.coop.CoopCourierScreen;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import hyperbolicOverthrall.cards.CourierCard;
import hyperbolicOverthrall.orbs.scrapPackage.AbstractScrap;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz= CoopCourierScreen.class,
        method= "purchaseCard"
)
public class CourierCardPatch {
    @SpireInsertPatch (
            locator =Locator.class
    )
    public static void methodToInsert(CoopCourierScreen __instance, AbstractCard hoveredCard, int i) {
        if(hoveredCard instanceof CourierCard) {
            ((CourierCard) hoveredCard).onSendCard(__instance.getRecipient());
        }
    }
    public static class Locator extends SpireInsertLocator {

        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "loseGold");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}


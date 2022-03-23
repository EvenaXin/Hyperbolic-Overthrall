package hyperbolicOverthrall.patches;

import chronoMods.coop.CoopCourierScreen;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hyperbolicOverthrall.characters.Altruist;
import hyperbolicOverthrall.characters.Anarchist;
import hyperbolicOverthrall.characters.Cognizant;
import hyperbolicOverthrall.characters.Executive;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static hyperbolicOverthrall.characters.Altruist.Enums.ALTRUIST;
import static hyperbolicOverthrall.characters.Anarchist.Enums.ANARCHIST;
import static hyperbolicOverthrall.characters.Cognizant.Enums.COGNIZANT;
import static hyperbolicOverthrall.characters.Executive.Enums.EXECUTIVE;

@SpirePatch(
        clz = CoopCourierScreen.class,
        method = "init"
)

public class BoosterColorsPatch {

    @SpirePostfixPatch
    public static void Insert(CoopCourierScreen __instance) {
        if (AbstractDungeon.player.chosenClass.equals(ALTRUIST)) {
            __instance.boosterTex[0] = Altruist.CommonPack;
            __instance.boosterTex[1] = Altruist.UncommonPack;
            __instance.boosterTex[2] = Altruist.RarePack;
        }
        else if (AbstractDungeon.player.chosenClass.equals(ANARCHIST)) {
            __instance.boosterTex[0] = Anarchist.CommonPack;
            __instance.boosterTex[1] = Anarchist.UncommonPack;
            __instance.boosterTex[2] = Anarchist.RarePack;
        }
        else if (AbstractDungeon.player.chosenClass.equals(COGNIZANT)) {
            __instance.boosterTex[0] = Cognizant.CommonPack;
            __instance.boosterTex[1] = Cognizant.UncommonPack;
            __instance.boosterTex[2] = Cognizant.RarePack;
        }
        else if (AbstractDungeon.player.chosenClass.equals(EXECUTIVE)) {
            __instance.boosterTex[0] = Executive.CommonPack;
            __instance.boosterTex[1] = Executive.UncommonPack;
            __instance.boosterTex[2] = Executive.RarePack;
        }
    }

    private static class locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CoopCourierScreen.class, "default");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
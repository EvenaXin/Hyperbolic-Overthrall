package hyperbolicOverthrall.patches;

import basemod.DevConsole;
import chronoMods.TogetherManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ConsoleEnablePatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "update"
    )
    public static class ConvenienceDebugPresses {
        public ConvenienceDebugPresses() {
        }

        public static void Postfix(AbstractDungeon __instance) {
            DevConsole.enabled = true;
        }
    }
}

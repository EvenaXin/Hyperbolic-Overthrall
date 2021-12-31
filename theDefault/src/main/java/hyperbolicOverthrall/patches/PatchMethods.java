package hyperbolicOverthrall.patches;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class PatchMethods {
    public static boolean getRunicRelics() {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (RelicTags.hasTag(relic.relicId, "RUNIC")) {
                return true;
            }
        }
        return false;
    }
    public static boolean getIceCreamRelics() {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (RelicTags.hasTag(relic.relicId, "ICECREAM")) {
                return true;
            }
        }
        return false;
    }
}

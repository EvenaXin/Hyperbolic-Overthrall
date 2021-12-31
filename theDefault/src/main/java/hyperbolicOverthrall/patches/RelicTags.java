package hyperbolicOverthrall.patches;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RelicTags {
    public static HashMap<String, ArrayList<String>> relicTags = new HashMap<String, ArrayList<String>>();
    public static boolean hasTag(String id, String tag) {
        if (relicTags.containsKey(id)) {
            if (relicTags.get(id).contains(tag)) {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<AbstractRelic> getRelicsWithTag(String tag) {
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        return relics;
    }
}

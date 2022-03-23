package hyperbolicOverthrall.orbs.scrapPackage;

import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;

import java.util.Map;

import static hyperbolicOverthrall.HyperbolicOverthrall.logger;

public class ScrapStrings {
    public String NAME;
    public String[] DESCRIPTION;

    public ScrapStrings() {
    }

    public static ScrapStrings getMockScrapString() {
        ScrapStrings retVal = new ScrapStrings();
        retVal.NAME = "[MISSING_NAME]";
        retVal.DESCRIPTION = LocalizedStrings.createMockStringArray(5);
        return retVal;
    }

    private static Map<String, ScrapStrings> scrap;

    public static ScrapStrings getScrapString(String scrapName) {
        if (scrap.containsKey(scrapName)) {
            return (ScrapStrings)scrap.get(scrapName);
        } else {
            logger.info("[ERROR] OrbStrings: " + scrapName + " not found");
            return ScrapStrings.getMockScrapString();
        }
    }
}

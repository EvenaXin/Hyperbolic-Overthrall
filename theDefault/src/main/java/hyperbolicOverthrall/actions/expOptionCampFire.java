package hyperbolicOverthrall.actions;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;

public class expOptionCampFire extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public expOptionCampFire(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        this.updateUsability(active);
    }

    public void updateUsability(boolean canUse) {
        this.description = canUse ? TEXT[1] : TEXT[2];
        this.img = ImageMaster.CAMPFIRE_SMITH_BUTTON;
    }

    @Override
    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new CampfireSmithEffect());
        }
    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Smith Option");
        TEXT = uiStrings.TEXT;
    }
}

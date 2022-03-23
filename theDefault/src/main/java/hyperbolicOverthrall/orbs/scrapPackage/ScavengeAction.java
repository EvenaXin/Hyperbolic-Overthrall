package hyperbolicOverthrall.orbs.scrapPackage;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.Iterator;

public class ScavengeAction extends AbstractGameAction {
    private AbstractScrap scrapType;
    private boolean autoEvoke;

    public ScavengeAction(AbstractScrap newScrapType, AbstractPlayer player) {
        this(newScrapType, player, true);
    }

    public ScavengeAction(AbstractScrap newScrapType, AbstractPlayer player, boolean autoEvoke) {
        this.autoEvoke = false;
        this.duration = Settings.ACTION_DUR_FAST;
        this.scrapType = newScrapType;
        this.autoEvoke = autoEvoke;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.autoEvoke) {
                ScavengeScrapMethod.scavengeScrap(this.scrapType, AbstractDungeon.player);
            } else {
                Iterator var1 = AbstractDungeon.player.orbs.iterator();

                while(var1.hasNext()) {
                    AbstractOrb o = (AbstractOrb)var1.next();
                    if (o instanceof EmptyOrbSlot) {
                        ScavengeScrapMethod.scavengeScrap(this.scrapType, AbstractDungeon.player);
                        break;
                    }
                }
            }

            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }

        this.tickDuration();
    }
}

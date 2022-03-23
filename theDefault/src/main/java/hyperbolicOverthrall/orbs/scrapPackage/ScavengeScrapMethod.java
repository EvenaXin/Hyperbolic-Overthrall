package hyperbolicOverthrall.orbs.scrapPackage;

import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import hyperbolicOverthrall.patches.AbstractPlayerScrapPatch;
import hyperbolicOverthrall.patches.ActionManagerScrapPatch;
import hyperbolicOverthrall.powers.AbstractScrapPower;

import java.util.Iterator;

import static hyperbolicOverthrall.patches.AbstractPlayerScrapPatch.maxScrap;
import static hyperbolicOverthrall.patches.AbstractPlayerScrapPatch.scrap;
import static hyperbolicOverthrall.patches.ActionManagerScrapPatch.scrapScavengedThisCombat;
import static hyperbolicOverthrall.patches.ActionManagerScrapPatch.scrapScavengedThisTurn;

public class ScavengeScrapMethod {
    public static void scavengeScrap(AbstractScrap scrapToSet, AbstractPlayer player) {
        if (maxScrap.get(player) <= 0) {
            AbstractDungeon.effectList.add(new ThoughtBubble(player.dialogX, player.dialogY, 3.0F, player.MSG[4], true));
        }
        else {
            if (maxScrap.get(player) > 0) {
                if (player.hasRelic("Dark Core") && !(scrapToSet instanceof Dart)) {
                    scrapToSet = new Dart(2);
                }
                int index = -1;

                int plasmaCount;
                for(plasmaCount = 0; plasmaCount < scrap.get(player).size(); ++plasmaCount) {
                    if (scrap.get(player).get(plasmaCount) instanceof EmptyScrapSlot) {
                        index = plasmaCount;
                        break;
                    }
                }

                if (index != -1) {
                    scrapToSet.cX = scrap.get(player).get(index).cX;
                    scrapToSet.cY = scrap.get(player).get(index).cY;
                    scrap.get(player).set(index, scrapToSet);
                    (scrap.get(player).get(index)).setSlot(index, maxScrap.get(player));
                    (scrapToSet).playChannelSFX();

                    for (AbstractPower p : player.powers) {
                        if(p instanceof AbstractScrapPower) {
                            AbstractScrapPower.onScavenge(scrapToSet);
                        }
                    }

                    scrapScavengedThisCombat.get(scrapScavengedThisCombat).add(scrapToSet);
                    scrapScavengedThisTurn.get(scrapScavengedThisTurn).add(scrapToSet);
                    plasmaCount = 0;

                    for ( AbstractScrap scrap : scrapScavengedThisTurn.get(scrapScavengedThisTurn))
                        if (scrap instanceof Dart) {
                            ++plasmaCount;
                        }
                    }
                }
                else {
                    AbstractDungeon.actionManager.addToTop(new ScavengeAction(scrapToSet, player));
                    AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(1));
                    AbstractDungeon.actionManager.addToTop(new AnimateOrbAction(1));
            }
        }
    }
}

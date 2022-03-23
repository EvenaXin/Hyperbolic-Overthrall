package hyperbolicOverthrall.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import chronoMods.TogetherManager;
import chronoMods.network.RemotePlayer;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hyperbolicOverthrall.HyperbolicOverthrall;
import hyperbolicOverthrall.util.TextureLoader;

import static hyperbolicOverthrall.HyperbolicOverthrall.makeRelicOutlinePath;
import static hyperbolicOverthrall.HyperbolicOverthrall.makeRelicPath;

public class LoneWolfRelic extends CustomRelic {

    public static final String ID = HyperbolicOverthrall.makeID("LoneWolfRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BottledPlaceholder.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BottledPlaceholder.png"));

    public LoneWolfRelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, IMG, tier, sfx);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.img = texture;
        this.outlineImg = texture;
    }

    public LoneWolfRelic() {
        super(ID, IMG, RelicTier.STARTER, LandingSound.FLAT);

    }

    @Override
    public void onEnterRoom (AbstractRoom room) {
        boolean highest = true;
        for(RemotePlayer player : TogetherManager.players) {
            if(AbstractDungeon.floorNum < player.floor) {
                highest = false;
            }
        }
        if(highest) {
            this.addToBot(new GainGoldAction(5 * TogetherManager.players.size()));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LoneWolfRelic();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

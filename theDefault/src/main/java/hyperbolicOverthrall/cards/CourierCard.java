package hyperbolicOverthrall.cards;

import chronoMods.network.RemotePlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class CourierCard extends AbstractDynamicCard {
    public int timesSent = 0;

    public CourierCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    public void onSendCard(RemotePlayer player) {
        timesSent += 1;
    }
}

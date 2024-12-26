package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class theUnknown extends AbstractTlipocaRelic{

    public static final String relicName = "theUnknown";
    public static final String ID = TlipocaMod.getID(relicName);

    public theUnknown() {
        super(relicName, RelicTier.BOSS, LandingSound.FLAT, true);

    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 3;
    }

    @Override
    public void onUnlock(AbstractCard c) {
        if (this.counter > 0 && c.cost > 0) {
            flash();
            c.modifyCostForCombat(-1);
            this.counter--;
        }
    }

    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.hasRelic(SpiritSeveranceTome.ID)){
            this.counter+=player.getRelic(SpiritSeveranceTome.ID).counter;
            player.relics.stream()
                    .filter(r -> r instanceof SpiritSeveranceTome).findFirst()
                    .map(r -> player.relics.indexOf(r))
                    .ifPresent(index -> instantObtain(player, index, true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new theUnknown();
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(SpiritSeveranceTome.ID);
    }
}

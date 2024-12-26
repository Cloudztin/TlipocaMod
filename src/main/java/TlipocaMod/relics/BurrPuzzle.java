package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BurrPuzzle extends AbstractTlipocaRelic{

    public static final String relicName = "BurrPuzzle";
    public static final String ID = TlipocaMod.getID(relicName);
    private boolean gained = true;
    private boolean drawn = true;

    public BurrPuzzle() {
        super(relicName, RelicTier.COMMON, LandingSound.SOLID, true);

    }

    public void atBattleStart() {
        gained = false;
        drawn = false;
    }

    public void onUnlock(AbstractCard c) {
        super.onUnlock(c);
        if (!gained) {
            gained = true;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
            if (drawn) this.grayscale = true;
        }
    }

    public void onLock(AbstractCard c) {
        super.onLock(c);
        if (!drawn) {
            drawn = true;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(1));
            if (gained) this.grayscale = true;
        }
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }



    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BurrPuzzle();
    }
}

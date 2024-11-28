package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class Schistosome extends AbstractTlipocaRelic{

    public static final String relicName = "Schistosome";
    public static final String ID = TlipocaMod.getID(relicName);
    private boolean activated;

    public Schistosome() {
        super(relicName, RelicTier.RARE, LandingSound.SOLID, true);
        activated = false;
    }

    public void atTurnStart() {
        this.grayscale=false;
        this.activated = false;
    }

    public void onMonsterDamaged() {
        if(!activated){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(2));
            this.grayscale=true;
            this.activated = true;
        }
    }

    public void onVictory() {
        this.grayscale=false;
        this.activated = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Schistosome();
    }
}

package TlipocaMod.relics;


import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SealofthePast extends AbstractTlipocaRelic {

    public static final String relicName = "SealofthePast";
    public static final String ID = TlipocaMod.getID(relicName);

    public SealofthePast() {
        super(relicName, RelicTier.COMMON, LandingSound.HEAVY,true);
    }

    public void atBattleStart(){
        stopPulse();
        if(AbstractDungeon.player.isBloodied) this.pulse=true;
    }

    public void onPlayerEndTurn() {
        if(AbstractDungeon.player.isBloodied){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3));
        }
    }

    public void onBloodied() {
        flash();
        this.pulse=true;
    }

    public void onNotBloodied() {
        flash();
        stopPulse();
    }

    public void onVictory() {
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SealofthePast();
    }
}

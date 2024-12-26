package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SpiritSeveranceTome extends AbstractTlipocaRelic{

    public static final String relicName = "SpiritSeveranceTome";
    public static final String ID = TlipocaMod.getID(relicName);
    private boolean triggered = false;

    public SpiritSeveranceTome() {
        super(relicName, RelicTier.STARTER, LandingSound.FLAT, true);

        this.triggered=false;
    }

    public void onUnlock(AbstractCard c) {
        if(!triggered){
            flash();
            addToBot(new MakeTempCardInHandAction(new Miracle(), 2));
            triggered=true;
            this.grayscale=true;
        }
    }

    @Override
    public void onVictory() {
        this.triggered=false;
        this.grayscale=false;
    }

    @Override
    public void atPreBattle() {
        this.triggered=false;
        this.grayscale=false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SpiritSeveranceTome();
    }

    public boolean canSpawn() {
        /* 25 */     return false;
        /*    */   }
}

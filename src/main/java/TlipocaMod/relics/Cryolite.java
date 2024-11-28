package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Cryolite extends AbstractTlipocaRelic implements CustomSavable<Boolean> {

    public static final String relicName = "Cryolite";
    public static final String ID = TlipocaMod.getID(relicName);

    public Cryolite() {
        super(relicName, RelicTier.COMMON, LandingSound.MAGICAL, true);
        this.grayscale=true;
        this.pulse=false;
    }


    public void atTurnStart() {
        if(!this.grayscale){
            this.flash();
            addToTop(new DrawCardAction(1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.grayscale=true;
            this.pulse=false;
        }
    }

    public void onVictory() {
        if(EnergyPanel.totalCount>0){
            flash();
            this.pulse=true;
            this.grayscale=false;
        }

    }

    public void onPlayerEndTurn() {
        if(EnergyPanel.totalCount>0){
            flash();
            this.grayscale=false;
            this.pulse=true;
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Cryolite();
    }

    public Boolean onSave() {
        return this.grayscale;
    }

    public void onLoad(Boolean gray) {
        this.grayscale = gray;
        this.pulse = !gray;
    }
}

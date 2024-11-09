package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class PowerUnleashAction extends AbstractGameAction {

    public PowerUnleashAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }


    public void update(){
        if(duration == Settings.ACTION_DUR_FAST){
            int EnergyAmt=0;
            for(AbstractCard c: AbstractDungeon.player.hand.group){
                if(c.cost>=0 ) EnergyAmt+=c.costForTurn;
                if(c.cost==-1) EnergyAmt+=EnergyPanel.getCurrentEnergy();
            }
            if(EnergyAmt>0)
                AbstractDungeon.actionManager.addToTop(new GainEnergyAction(EnergyAmt));

        }
        this.isDone=true;
    }
}

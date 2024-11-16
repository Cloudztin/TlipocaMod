package TlipocaMod.action;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SummonAction extends AbstractGameAction {
    private final int aimCost;
    public AbstractPlayer p;

    public SummonAction(int aimCost) {
        this.aimCost = aimCost;
        this.p= AbstractDungeon.player;
        this.duration= Settings.ACTION_DUR_MED;
    }

    public SummonAction() {
        this.aimCost= -2;
        this.p=AbstractDungeon.player;
        this.duration= Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration != Settings.ACTION_DUR_MED) {
            tickDuration();
            return;
        }
        if(this.p.hand.size()>= BaseMod.MAX_HAND_SIZE || (this.p.drawPile.isEmpty() && this.p.discardPile.isEmpty()) ) {
            this.isDone = true;
            return;
        }

        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.isDone = true;
            return;
        }

        if(this.p.drawPile.isEmpty()){
            addToTop(new SummonAction(this.aimCost));
            addToTop(new EmptyDeckShuffleAction());

            this.isDone=true;
            return;
        }

        if(this.aimCost>=0){
            final AbstractCard c = this.p.drawPile.group.get(this.p.drawPile.size() - 1);
            if(c.cost>=0 && c.costForTurn>=this.aimCost){
                addToTop(new DrawCardAction(1));
                this.isDone = true;
                return;
            }
            if(c.cost==-1 && EnergyPanel.getCurrentEnergy()>=this.aimCost){
                addToTop(new DrawCardAction(1));
                this.isDone = true;
                return;
            }
            addToTop(new SummonAction(this.aimCost));
            addToTop(new DrawCardAction(1));
        }
        else if(this.aimCost==-1){
            final AbstractCard c = this.p.drawPile.group.get(this.p.drawPile.size() - 1);
            if(c.cost>=aimCost){
                addToTop(new DrawCardAction(1));
                this.isDone = true;
                return;
            }
            addToTop(new SummonAction(this.aimCost));
            addToTop(new DrawCardAction(1));
        }
        else if(this.aimCost== -2){
            int maxCost= -1;
            for(AbstractCard c:this.p.hand.group){
                if(c.cost>=0 && c.costForTurn>maxCost)
                    maxCost= c.costForTurn;
                if(c.cost==-1 && EnergyPanel.getCurrentEnergy()>maxCost)
                    maxCost=EnergyPanel.getCurrentEnergy();
            }
            addToTop(new SummonAction(maxCost));
        }

        this.isDone = true;
        return;
    }
}

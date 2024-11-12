package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;

public class ExtractionAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ExtractionAction").TEXT;
    private final List<AbstractCard> cannotSelect=new ArrayList<>();


    public ExtractionAction() {
        this.duration= Settings.ACTION_DUR_FAST;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int avai=0;
            for(AbstractCard c: AbstractDungeon.player.hand.group)
                if(c.cost >=-1) avai++;
            if(avai==0){
                this.isDone=true;

                return;
            }

            if(avai==1){
                for(AbstractCard c: AbstractDungeon.player.hand.group)
                    if(c.cost >=-1){
                        if(c.cost>=0){
                            addToTop(new IncreaseCostForTurnAction(c, c.costForTurn));
                            addToTop(new GainEnergyAction(c.costForTurn));
                        }
                        if(c.cost==-1)
                            addToTop(new GainEnergyAction(EnergyPanel.totalCount));

                        this.isDone=true;
                        return;
                    }
            }
            if(avai>1){

                for(AbstractCard c: AbstractDungeon.player.hand.group)
                    if(c.cost< -1) cannotSelect.add(c);

                AbstractDungeon.player.hand.group.removeAll(cannotSelect);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0],1,false, false, false, false);

                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;


            }



        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                if(c.cost>=0){
                    addToTop(new IncreaseCostForTurnAction(c, c.costForTurn));
                    addToTop(new GainEnergyAction(c.costForTurn));
                }
                if(c.cost==-1)
                    addToTop(new GainEnergyAction(EnergyPanel.totalCount));

                c.applyPowers();
                AbstractDungeon.player.hand.addToTop(c);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;

        }



    }

    private void returnCards(){
        for(AbstractCard c:this.cannotSelect)
            AbstractDungeon.player.hand.addToTop(c);
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}

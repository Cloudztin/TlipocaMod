package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;

public class ReverseFateAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ReverseFateAction").TEXT;
    public boolean isRandom;
    private final List<AbstractCard> notMax=new ArrayList<AbstractCard>();

    public ReverseFateAction(boolean isRandom) {
        this.isRandom = isRandom;
        this.duration = 0.3F;
    }

    public void update() {
        if (this.duration == 0.3F) {
            int maxCost = -1;
            for(AbstractCard c: AbstractDungeon.player.hand.group) {
                if(c.cost==-1)
                    if(EnergyPanel.getCurrentEnergy()>maxCost)
                        maxCost = EnergyPanel.getCurrentEnergy();
                if(c.cost>=0)
                    if(c.costForTurn>maxCost)
                        maxCost = c.costForTurn;
            }
            if(maxCost==-1){
                this.isDone=true;
                return;
            }
            int cardAmt=0;
            for(AbstractCard c: AbstractDungeon.player.hand.group) {
                if(c.cost==-1)
                    if(EnergyPanel.getCurrentEnergy()==maxCost)
                        cardAmt++;
                if(c.cost>=0)
                    if(c.costForTurn==maxCost)
                        cardAmt++;
            }
            if(cardAmt==1){
                AbstractCard card1 = null;
                for(AbstractCard c: AbstractDungeon.player.hand.group)
                    if((c.cost==-1 && EnergyPanel.getCurrentEnergy()==maxCost) || (c.cost>=0 && c.costForTurn==maxCost)) card1=c;

                AbstractDungeon.player.hand.moveToDeck(card1, false);
                addToBot(new AutoplayTmpCardAction(card1));

                this.isDone=true;
                return;
            }
            if(cardAmt>1){

                for(AbstractCard c: AbstractDungeon.player.hand.group) {
                    if(c.cost==-1 && EnergyPanel.getCurrentEnergy()<maxCost)
                        this.notMax.add(c);

                    if(c.cost>=0 && c.costForTurn<maxCost)
                        this.notMax.add(c);
                }
                AbstractDungeon.player.hand.group.removeAll(this.notMax);

                if(this.isRandom){
                    AbstractCard card=AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRng);
                    AbstractDungeon.player.hand.moveToDeck(card, false);
                    addToBot(new AutoplayTmpCardAction(card));
                    returnCards();

                    this.isDone=true;
                }
                else{
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                    tickDuration();
                }
                return;

            }
        }

        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                AbstractDungeon.player.hand.moveToDeck(c, false);
                addToBot(new AutoplayTmpCardAction(c));
            }

            returnCards();
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();

    }

    private void returnCards(){
        for(AbstractCard c:this.notMax)
            AbstractDungeon.player.hand.addToTop(c);
       AbstractDungeon.player.hand.refreshHandLayout();
    }
}

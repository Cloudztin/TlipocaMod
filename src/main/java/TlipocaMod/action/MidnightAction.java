package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.lwjgl.opengl.NVFence;

import java.util.ArrayList;
import java.util.List;

public class MidnightAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ReverseFateAction").TEXT;
    public boolean isRandom;
    private final List<AbstractCard> notMax=new ArrayList<AbstractCard>();

    public MidnightAction(boolean isRandom) {
        this.isRandom = isRandom;
        this.duration = 0.3F;
    }

    public void update() {
        if (this.duration == 0.3F) {
            int maxCost = -1;
            for(AbstractCard c: AbstractDungeon.player.hand.group) {
                if(c.cost==-1 && EnergyPanel.getCurrentEnergy()>maxCost)
                    maxCost = EnergyPanel.getCurrentEnergy();

                if(c.cost>=0 && c.costForTurn>maxCost)
                    maxCost = c.costForTurn;
            }
            if(maxCost==-1){
                this.isDone=true;
                return;
            }

            int cardAmt=0;
            for(AbstractCard c: AbstractDungeon.player.hand.group)
                if((c.cost==-1 && EnergyPanel.getCurrentEnergy()==maxCost) || (c.cost>=0 && c.costForTurn==maxCost)) cardAmt++;

            if(cardAmt==1){
                AbstractCard card1 = null;
                for(AbstractCard c: AbstractDungeon.player.hand.group)
                    if((c.cost==-1 && EnergyPanel.getCurrentEnergy()==maxCost) || (c.cost>=0 && c.costForTurn==maxCost)) card1=c;

                CardPatch.newVarField.rebound.set(card1, true);
                addToBot(new PlayHandAction(card1, false));

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
                    CardPatch.newVarField.rebound.set(card, true);
                    addToBot(new PlayHandAction(card, false));
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
                CardPatch.newVarField.rebound.set(c, true);
                c.applyPowers();
                AbstractDungeon.player.hand.addToTop(c);
                addToBot(new PlayHandAction(c, false));
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

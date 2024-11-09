package TlipocaMod.action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class IncreaseHandCostForCombatAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("IncreaseHandCost").TEXT;
    private List<AbstractCard> cannotChange=new ArrayList<>();
    private AbstractPlayer p= AbstractDungeon.player;
    private boolean isRandom;
    public int cost;
    public int amount;

    public IncreaseHandCostForCombatAction(int amount, boolean isRandom, int cost) {
        this.amount = amount;
        this.isRandom = isRandom;
        this.cost = cost;
        this.duration = 0.3F;
    }

    public void update() {
        if (this.duration == 0.3F) {
            int avai=0;
            for(AbstractCard c: AbstractDungeon.player.hand.group)
                if(c.cost>=0) avai++;
            if(avai<= amount) {
                amount = avai;
                for(AbstractCard c: AbstractDungeon.player.hand.group)
                    if(c.cost>=0) c.updateCost(cost);
                this.isDone = true;
            }
            else{
                if(this.isRandom) {
                    List<AbstractCard> selected = new ArrayList<>();
                    findRandomCard(amount,selected);
                    this.isDone=true;
                    tickDuration();
                }
                else{
                    for(AbstractCard c: this.p.hand.group)
                        if(c.cost<0) this.cannotChange.add(c);
                    this.p.hand.group.removeAll(this.cannotChange);

                    if(this.p.hand.size()>amount){
                        if(amount<0){
                            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                            AbstractDungeon.player.hand.applyPowers();
                            tickDuration();
                        }
                        else{
                            AbstractDungeon.handCardSelectScreen.open(TEXT[0],amount,false, false, false, false);
                            AbstractDungeon.player.hand.applyPowers();
                            tickDuration();
                        }
                    }
                    else{
                        if(amount>0){
                            for(AbstractCard c: this.p.hand.group){
                                c.updateCost(cost);
                                c.superFlash(Color.WHITE.cpy());
                            }
                            returnCards();
                            this.isDone=true;
                        }
                    }
                }
            }
        } else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                c.updateCost(cost);
                c.applyPowers();
                this.p.hand.addToTop(c);
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.p.hand.refreshHandLayout();
            this.isDone = true;
        }

    }

    public void findRandomCard(int amt, List<AbstractCard> selected) {
        if(amt <=0)
            return;
        AbstractCard c= p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if(c.cost>=0 && !selected.contains(c)){
            c.updateCost(cost);
            c.superFlash(Color.WHITE.cpy());
            selected.add(c);
            findRandomCard(amt-1, selected);
        }
        else findRandomCard(amt, selected);
    }

    private void returnCards(){
        for(AbstractCard c:this.cannotChange)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}

package TlipocaMod.action;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class ReduceHandCostAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ReduceHandCost").TEXT;
    private List<AbstractCard> cannotReduce=new ArrayList<>();
    private AbstractPlayer p= AbstractDungeon.player;
    private boolean isRandom;
    public int cost;
    public int amount;

    public ReduceHandCostAction(int amount, boolean isRandom, int cost){
        this.amount=amount;
        this.isRandom=isRandom;
        this.cost=cost;
        this.duration=.25F;
    }

    public void update() {
        if(this.duration==.25F){
            for(AbstractCard c: this.p.hand.group)
                if(c.cost<0 || c.costForTurn==0) this.cannotReduce.add(c);

            if(this.p.hand.size()-this.cannotReduce.size()<=amount){
                for(AbstractCard c: this.p.hand.group)
                    if(c.cost>0 && c.costForTurn>0) CardModifierManager.addModifier(c, new CostForTurnModifier(-this.cost));
                this.isDone=true;
                tickDuration();
                return;
            }
            else{

                this.p.hand.group.removeAll(this.cannotReduce);

                if(this.p.hand.size()<=amount){
                    for(AbstractCard c: this.p.hand.group)
                        CardModifierManager.addModifier(c, new CostForTurnModifier(-this.cost));
                    returnCards();
                    this.isDone=true;
                    tickDuration();
                    return;
                }
                else{
                    if(this.isRandom){
                        List<AbstractCard> selected=new ArrayList<>();
                        findRandomCard(this.amount, selected);
                        returnCards();
                        this.isDone=true;
                        tickDuration();
                        return;
                    }
                    else{
                        if(this.amount>0){
                            AbstractDungeon.handCardSelectScreen.open(TEXT[0],amount,false, false, false, false);
                            AbstractDungeon.player.hand.applyPowers();
                            tickDuration();
                            return;
                        }
                        else{
                            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                            AbstractDungeon.player.hand.applyPowers();
                            tickDuration();
                            return;
                        }
                    }
                }
            }
        }
        else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                CardModifierManager.addModifier(c, new CostForTurnModifier(-this.cost));
                c.applyPowers();
                this.p.hand.addToTop(c);
                c.superFlash(Color.WHITE.cpy());
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }


    }

    private void findRandomCard(int amt, List<AbstractCard> selected){
        if(amt<=0) return;
        AbstractCard c= this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if(c.cost<0 || selected.contains(c) || c.costForTurn==0) findRandomCard(amt,selected);
        else{
            CardModifierManager.addModifier(c, new CostForTurnModifier(-this.cost));
            c.superFlash(Color.WHITE.cpy());
            selected.add(c);
            findRandomCard(amt-1,selected);
        }
    }

    private void returnCards(){
        for(AbstractCard c:this.cannotReduce)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }

}

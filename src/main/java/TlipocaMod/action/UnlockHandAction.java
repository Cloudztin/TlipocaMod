package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class UnlockHandAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("UnlockHandAction").TEXT;
    private final List<AbstractCard> cannotChange=new ArrayList<>();
    private final AbstractPlayer p= AbstractDungeon.player;
    private final boolean isRandom;

    public UnlockHandAction(int amount, boolean isRandom) {
        this.amount = amount;
        this.isRandom = isRandom;
        this.duration = 0.3F;
    }

    public void update() {
        if(this.duration == 0.3F) {
            int avai=0;
            for(AbstractCard c: p.hand.group)
                if(CardPatch.newVarField.lockNUM.get(c)>0)
                        avai++;

            if(avai<=amount) {
                for(AbstractCard c: p.hand.group)
                    if(CardPatch.newVarField.lockNUM.get(c)>0)
                            CardPatch.zeroLock(c);

                this.isDone=true;
                return;
            }
            else{

                for(AbstractCard c: p.hand.group)
                    if(CardPatch.newVarField.lockNUM.get(c)==0)
                        this.cannotChange.add(c);

                p.hand.group.removeAll(this.cannotChange);

                if(this.p.hand.size()>amount){
                    if(isRandom){
                        List<AbstractCard> selected = new ArrayList<>();
                        findRandomCard(this.amount, selected);

                        returnCards();
                        this.isDone=true;
                        return;
                    }

                    if(amount<0){
                        AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                        AbstractDungeon.player.hand.applyPowers();
                        tickDuration();
                        return;
                    }
                    else {
                        AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount,false, false, false, false);
                        AbstractDungeon.player.hand.applyPowers();
                        tickDuration();
                        return;
                    }
                }
                else{
                    if(amount>0){
                        for(AbstractCard c: p.hand.group){
                            CardPatch.zeroLock(c);
                            c.superFlash(Color.WHITE.cpy());
                        }

                        returnCards();
                        this.isDone=true;
                        tickDuration();
                        return;
                    }
                }
            }
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                CardPatch.zeroLock(c);
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

    public void findRandomCard(int amt, List<AbstractCard> selected) {
        if(amt <=0)
            return;
        AbstractCard c= p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if(CardPatch.newVarField.lockNUM.get(c)>0){
            CardPatch.zeroLock(c);
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

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

public class AdaptAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("AdaptAction").TEXT;
    private final List<AbstractCard> cannotChange=new ArrayList<>();
    private final AbstractPlayer p= AbstractDungeon.player;

    public AdaptAction(int amount) {
        this.amount = amount;
        this.duration = 0.3F;
    }

    public void update() {
        if(this.duration == 0.3F) {
            int avai=0;
            for(AbstractCard c: p.hand.group)
                if(CardPatch.newVarField.canLock.get(c))
                    avai++;


            if(avai==0) {
                this.isDone = true;
            }
            else{

                for(AbstractCard c: p.hand.group)
                    if(!CardPatch.newVarField.canLock.get(c))
                        this.cannotChange.add(c);

                p.hand.group.removeAll(this.cannotChange);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount,true, true, false, false);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();

            }
            return;
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for(AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
                if(CardPatch.newVarField.canLock.get(c)){
                    if(CardPatch.newVarField.lockNUM.get(c)>0)
                        CardPatch.zeroLock(c);
                    else
                        CardPatch.intoLock(c, 1);
                }
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

    private void returnCards(){
        for(AbstractCard c:this.cannotChange)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}

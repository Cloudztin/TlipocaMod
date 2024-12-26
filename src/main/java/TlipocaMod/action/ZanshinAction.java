package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class ZanshinAction extends AbstractGameAction {

    private final AbstractPlayer p;
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ZanshinAction").TEXT;
    private final List<AbstractCard> cannotChange=new ArrayList<>();

    public ZanshinAction(AbstractPlayer p) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            int avai=0;
            for(AbstractCard c : p.hand.group)
                if(CardPatch.newVarField.canLock.get(c))
                    avai++;

            if(avai==0){
                this.isDone=true;
                return;

            }

            if(avai==1){

                for(AbstractCard c : p.hand.group)
                    if(CardPatch.newVarField.canLock.get(c)){
                        int layers=0;
                        for(AbstractCard c2:p.hand.group)
                            if(!c2.equals(c)){
                                layers++;
                                if(c2.canUpgrade()){
                                    c2.upgrade();
                                    c2.superFlash(Color.GOLD.cpy());
                                }
                            }
                        CardPatch.lockNumUp(c, layers);
                        break;
                    }

                this.isDone=true;
                return;
            }

            for(AbstractCard c : p.hand.group)
                if(!CardPatch.newVarField.canLock.get(c))
                    cannotChange.add(c);

            p.hand.group.removeAll(cannotChange);

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            returnCards();
            AbstractCard c=AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
            int layers=p.hand.group.size();
            for(AbstractCard c2:p.hand.group)
                if(c2.canUpgrade()){
                    c2.upgrade();
                    c2.superFlash(Color.GOLD.cpy());
                }
            p.hand.addToTop(c);
            CardPatch.lockNumUp(c, layers);
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

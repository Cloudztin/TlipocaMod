package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class SublimationAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("SublimationAction").TEXT;
    private final List<AbstractCard> cannotChange=new ArrayList<>();
    private final AbstractPlayer p= AbstractDungeon.player;
    private final AbstractCard card;

    public SublimationAction(AbstractCard card) {
        this.card = card;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if(this.duration == Settings.ACTION_DUR_MED) {
            int avai=0;
            for(AbstractCard c:p.hand.group)
                if(CardPatch.newVarField.lockNUM.get(c)>0 || c.cost== -2)
                    avai++;
            if(avai==0) {
                this.isDone=true;
                return;
            }

            if(avai==1){
                for(AbstractCard c:p.hand.group)
                    if(CardPatch.newVarField.lockNUM.get(c)>0 || c.cost== -2){
                        p.hand.group.remove(c);
                        addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
                        break;
                    }
                this.isDone=true;
                return;
            }

            if(avai>1){
                for(AbstractCard c:p.hand.group)
                    if(CardPatch.newVarField.lockNUM.get(c)==0 && c.cost != -2)
                        cannotChange.add(c);

                p.hand.group.removeAll(cannotChange);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
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

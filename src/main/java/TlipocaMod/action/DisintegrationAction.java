package TlipocaMod.action;

import TlipocaMod.cards.tempCards.hllFetter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class DisintegrationAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("DisintegrationAction").TEXT;
    private final AbstractPlayer p= AbstractDungeon.player;
    private final List<AbstractCard> cannotChange=new ArrayList<>();

    public DisintegrationAction() {
        duration= Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_MED) {
            boolean avai=false;
            for(AbstractCard c:p.hand.group)
                if(!(c instanceof hllFetter)){
                    avai=true;
                    break;
                }

            if(!avai){
                this.isDone=true;
                return;
            }

            for(AbstractCard c:p.hand.group)
                if(c instanceof hllFetter)
                    cannotChange.add(c);

            p.hand.group.removeAll(cannotChange);


            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99,true, true, false, false);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int amt=AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            if(amt>0) {
                addToTop(new GainEnergyAction(amt));
                addToTop(new DrawCardAction(amt));
                addToTop(new StandByAction(0.4f));
                addToTop(new MakeTempCardInHandAction(new hllFetter(), amt));
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

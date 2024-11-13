package TlipocaMod.action;

import TlipocaMod.cards.tempCards.tlSentence;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ScaleAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ScaleAction").TEXT;
    private final boolean upgraded;

    public ScaleAction(boolean upgraded) {
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if(AbstractDungeon.player.hand.isEmpty()){
                this.isDone = true;

                return;
            }
            if(AbstractDungeon.player.hand.size() == 1){
                AbstractCard c= AbstractDungeon.player.hand.getTopCard();
                if(c.cost >= 0){
                    AbstractCard c2= new tlSentence();
                    ((tlSentence) c2).setX(c.costForTurn);
                    if(this.upgraded)
                        c2.freeToPlayOnce = true;
                    addToTop(new MakeTempCardInHandAction(c2));
                    AbstractDungeon.player.hand.moveToExhaustPile(c);
                }
                if(c.cost==-1){
                    AbstractCard c2= new tlSentence();
                    ((tlSentence) c2).setX(EnergyPanel.totalCount);
                    if(this.upgraded)
                        c2.freeToPlayOnce = true;
                    addToTop(new MakeTempCardInHandAction(c2));
                    AbstractDungeon.player.hand.moveToExhaustPile(c);
                }

                this.isDone=true;
                return;
            }
            if(AbstractDungeon.player.hand.size() >1){
                AbstractDungeon.handCardSelectScreen.open(TEXT[0],1,false, false, false, false);

                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }

        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard c= AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
            if(c.cost >= 0){
                AbstractCard c2= new tlSentence();
                ((tlSentence) c2).setX(c.costForTurn);
                if(this.upgraded)
                    c2.freeToPlayOnce = true;
                addToTop(new MakeTempCardInHandAction(c2));
                AbstractDungeon.player.hand.moveToExhaustPile(c);
            }
            if(c.cost==-1){
                AbstractCard c2= new tlSentence();
                ((tlSentence) c2).setX(EnergyPanel.totalCount);
                if(this.upgraded)
                    c2.freeToPlayOnce = true;
                addToTop(new MakeTempCardInHandAction(c2));
                AbstractDungeon.player.hand.moveToExhaustPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

    }

}

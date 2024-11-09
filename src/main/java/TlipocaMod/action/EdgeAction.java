package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EdgeAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ParcloseAction").TEXT;
    private AbstractPlayer p;

    public EdgeAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.p = (AbstractPlayer)target;
        setValues(target, source);
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    public void update() {
        if (this.duration == 0.5F) {
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                tickDuration();
                return;
            }
            else{
                if(this.p.hand.isEmpty()){
                    this.isDone=true;
                    return;
                }
                else{
                    final AbstractCard c = this.p.hand.getTopCard();
                    int amt=0;
                    if(c.costForTurn > 0)
                        amt=c.costForTurn;
                    else if(c.cost==-1 && EnergyPanel.totalCount >0)
                        amt=EnergyPanel.totalCount;
                    if(amt>0)
                        addToTop(new MakeTempCardInHandAction(new Shiv(), amt));
                    this.p.hand.moveToDeck(c, false);

                    this.isDone=true;

                    return;
                }
            }
        }
        else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                int amt=0;
                if(c.costForTurn > 0)
                    amt=c.costForTurn;
                else if(c.cost==-1 && EnergyPanel.totalCount >0)
                    amt=EnergyPanel.totalCount;
                if(amt>0)
                    addToTop(new MakeTempCardInHandAction(new Shiv(), amt));
                this.p.hand.moveToDeck(c, false);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            this.isDone=true;
        }

    }

}

package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ParcloseAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ParcloseAction").TEXT;
    private AbstractPlayer p;
    private AbstractCard card;

    public ParcloseAction(AbstractCreature target, AbstractCreature source, AbstractCard card) {
        this.card = card;
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
                        if(c.costForTurn > 0)
                            addToBot(new GainBlockAction( this.p, this.p, c.costForTurn * card.block));
                        else if(c.cost==-1 && EnergyPanel.getCurrentEnergy() >0)
                            addToBot(new GainBlockAction( this.p, this.p, EnergyPanel.getCurrentEnergy() * card.block));
                        this.p.hand.moveToDeck(c, false);

                        this.isDone=true;
                        return;
                    }
                }
        }
        else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if(c.costForTurn > 0)
                    addToBot(new GainBlockAction( this.p, this.p, c.costForTurn * card.block));
                else if(c.cost==-1 && EnergyPanel.totalCount >0)
                    addToBot(new GainBlockAction( this.p, this.p, EnergyPanel.totalCount * card.block));
                this.p.hand.moveToDeck(c, false);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            this.isDone=true;
        }
    }
}


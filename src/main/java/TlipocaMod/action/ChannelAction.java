package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChannelAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ChannelAction").TEXT;
    private final AbstractCard c;
    private final AbstractPlayer p;

    public ChannelAction(AbstractCard c, AbstractPlayer p) {
        this.c = c;
        this.p = p;
        duration= Settings.ACTION_DUR_MED;
    }

    public void update() {
        if(duration == Settings.ACTION_DUR_MED) {
            if(p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            if(p.hand.size() == 1) {
                AbstractCard card = p.hand.getTopCard();
                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                p.hand.removeCard(card);

                this.isDone=true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1,false, false, false, false);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard card = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
            addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone=true;
        }
    }
}

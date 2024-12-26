package TlipocaMod.action;

import TlipocaMod.cards.rare.hllTao;
import TlipocaMod.cards.tempCards.hllFetter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class TaoAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("TaoAction").TEXT;
    private final List<AbstractCard> cannotChange=new ArrayList<>();
    private final hllTao card;


    public TaoAction(hllTao card) {
        this.card = card;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_MED) {
            int avai=0;
            for(AbstractCard c: AbstractDungeon.player.hand.group)
                if(!(c instanceof hllFetter) && !c.tags.contains(AbstractCard.CardTags.HEALING) && !c.equals(card))
                    avai++;

            if(avai>0)
                for(AbstractCard c: AbstractDungeon.player.hand.group)
                    if(c instanceof hllFetter || c.tags.contains(AbstractCard.CardTags.HEALING) || c.equals(card))
                        cannotChange.add(c);

            AbstractDungeon.player.hand.group.removeAll(cannotChange);

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, true, true);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard c=AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
            card.ExhaustedCards.add(c);
            addToTop(new MakeTempCardInHandAction(new hllFetter()));

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
            returnCards();
        }
    }

    private void returnCards(){
        for(AbstractCard c:this.cannotChange)
            AbstractDungeon.player.hand.addToTop(c);
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}

package TlipocaMod.action;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DivineAction extends AbstractGameAction {
    private final int amt;
    private final AbstractCard card;

    public DivineAction(AbstractCard card, int amount) {
        this.card = card;
        this.amt = amount;
    }

    public void update() {
        if(!AbstractDungeon.player.hand.contains(card) || amt <=0) {
            this.isDone = true;
            return;
        }
        if(card.upgraded){
            addToTop(new GainEnergyAction(1));
            addToTop(new ExpertiseAction(AbstractDungeon.player, BaseMod.MAX_HAND_SIZE));
            addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.hand));

        }
        else{

            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    if(amt + AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE)
                        addToTop(new GainEnergyAction(1));
                    addToTop(new DrawCardAction(amt));

                    this.isDone=true;
                }
            });
            addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.hand));
        }

        this.isDone=true;
    }
}

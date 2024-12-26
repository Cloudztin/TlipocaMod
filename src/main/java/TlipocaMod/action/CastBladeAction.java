package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import TlipocaMod.cards.tempCards.hllTheRenowned;

public class CastBladeAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractCard card;

    public CastBladeAction(AbstractPlayer p, AbstractCard card) {
        this.p = p;
        this.card = card;
    }

    public void update() {
        int count = p.hand.size();

        hllTheRenowned c = new hllTheRenowned();
        c.setX(count);
        if (card.upgraded)
            c.freeToPlayOnce = true;

        addToTop(new MakeTempCardInHandAction(c, 1));

        for (int i = 0; i < count; i++)
            addToTop(new ExhaustAction(1, true, true));

        this.isDone = true;
    }
}

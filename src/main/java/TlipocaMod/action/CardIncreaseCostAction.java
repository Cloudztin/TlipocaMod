package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardIncreaseCostAction extends AbstractGameAction {

    private final AbstractCard card;

    public CardIncreaseCostAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        card.updateCost(1);

        this.isDone=true;
    }
}

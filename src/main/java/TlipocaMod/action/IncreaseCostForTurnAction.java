package TlipocaMod.action;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class IncreaseCostForTurnAction extends AbstractGameAction {
    private final AbstractCard targetCard;

    public IncreaseCostForTurnAction(AbstractCard card, int amt) {
        this.targetCard = card;
        this.amount = amt;
    }

    public void update() {
        CardModifierManager.addModifier(this.targetCard, new CostForTurnModifier(this.amount));
        this.targetCard.flash();
        this.isDone = true;
    }

}

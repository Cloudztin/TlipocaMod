package TlipocaMod.action;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CostForTurnSettleAction extends AbstractGameAction {

    private final AbstractCard card;
    private final CostForTurnModifier modifier;

    public CostForTurnSettleAction(AbstractCard card, CostForTurnModifier modifier) {
        this.card = card;
        this.modifier = modifier;
    }

    public void update() {
        if(modifier.trueCost!=-1){
            card.cost=Math.max(modifier.trueCost, 0);
            card.costForTurn=card.cost;
            modifier.trueCost = -1 ;
            card.isCostModifiedForTurn=false;
            card.isCostModified=false;
            CardModifierManager.removeSpecificModifier(card, modifier, false);

        }

        card.isCostModifiedForTurn=false;
        card.isCostModified=false;
        CardModifierManager.removeSpecificModifier(card, modifier, false);


        this.isDone=true;
    }
}

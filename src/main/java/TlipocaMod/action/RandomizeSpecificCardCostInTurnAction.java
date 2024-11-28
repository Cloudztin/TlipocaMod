package TlipocaMod.action;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;


public class RandomizeSpecificCardCostInTurnAction extends AbstractGameAction {

    private final AbstractCard card;

    public RandomizeSpecificCardCostInTurnAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        if(card.cost <0) {
            this.isDone = true;

            return;
        }
        int newCost = AbstractDungeon.cardRandomRng.random(3);
        if(card.cost != newCost)
            CardModifierManager.addModifier(card, new CostForTurnModifier(newCost-card.cost));
        card.costForTurn=newCost;
        card.flash();
        this.isDone=true;


    }
}

package TlipocaMod.action;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;


public class RandomizeSpecificCardCostAction extends AbstractGameAction {

    private final AbstractCard card;

    public RandomizeSpecificCardCostAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        if(card.cost <0) {
            this.isDone = true;

            return;
        }
        int newCost = AbstractDungeon.cardRandomRng.random(3);
        if(card.cost != newCost)
            card.isCostModified=true;
        card.cost = newCost;
        card.costForTurn=newCost;
        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
        if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
            if(((CostForTurnModifier) mods.get(0)).trueCost>=0)
                ((CostForTurnModifier) mods.get(0)).trueCost = newCost;

        this.isDone=true;


    }
}

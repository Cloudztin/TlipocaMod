package TlipocaMod.TlipocaMod;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

@AbstractCardModifier.SaveIgnore
public class CostForTurnModifier extends AbstractCardModifier{

    public static String ID;


    public int trueCost ;
    public int costBiasJustApplied;

    public CostForTurnModifier(){
        trueCost=-1;
        costBiasJustApplied = 0;
    }

    public CostForTurnModifier(int bias){
        trueCost=-1;
        costBiasJustApplied=bias;
    }


    public void applyModify(final AbstractCard card){
        if(trueCost == -1)
            trueCost=card.cost;
        if(costBiasJustApplied !=0 ){

            int tmpCost = card.cost;
            int diff = card.cost - card.costForTurn;
            tmpCost += costBiasJustApplied;
            if (tmpCost < 0)
                tmpCost = 0;

            card.isCostModified = tmpCost != trueCost;

            if(tmpCost !=card.cost){
                card.cost = tmpCost;
                card.costForTurn = card.cost - diff;
                if (card.costForTurn < 0)
                    card.costForTurn = 0;
            }




            costBiasJustApplied=0;
        }
    }

    public boolean shouldApply(final AbstractCard card){
        final ArrayList<AbstractCardModifier> mods =  CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
        if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier) {
            System.out.println("cost modifier found");
            ((CostForTurnModifier)mods.get(0)).costBiasJustApplied=this.costBiasJustApplied;

            ((CostForTurnModifier)mods.get(0)).applyModify(card);

            return false;
        }
        return true;
    }

    public void onInitialApplication(final AbstractCard card) {
        this.applyModify(card);
    }

    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if(trueCost != -1)
            AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            public void update() {
                card.cost = Math.max(trueCost, 0);
                card.costForTurn=card.cost;
                trueCost = -1 ;
                card.isCostModifiedForTurn=false;
                card.isCostModified=false;
                this.isDone = true;
            }
        });
    }


    public AbstractCardModifier makeCopy() {
        final CostForTurnModifier copy = new CostForTurnModifier();
        copy.trueCost = this.trueCost;
        copy.costBiasJustApplied = 0;

        return copy;
    }

    public String identifier(final AbstractCard card) {
        return CostForTurnModifier.ID;
    }

    static {
        CostForTurnModifier.ID="TlipocaMod:CostForTurnModifier";
    }
}

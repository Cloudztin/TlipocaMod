package TlipocaMod.TlipocaMod;

import TlipocaMod.patches.CardPatch;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;

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
            int tmpCostForTurn = card.costForTurn;
            tmpCost += costBiasJustApplied;
            tmpCostForTurn += costBiasJustApplied;
            if (tmpCost < 0)
                tmpCost = 0;
            if(tmpCostForTurn < 0)
                tmpCostForTurn = 0;

            card.isCostModified = tmpCost != trueCost;

            if(tmpCost !=card.cost)
                card.cost = tmpCost;
            if(tmpCostForTurn !=card.costForTurn)
                card.costForTurn = tmpCostForTurn;





            costBiasJustApplied=0;
        }
    }

    public boolean shouldApply(final AbstractCard card){
        final ArrayList<AbstractCardModifier> mods =  CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
        if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier) {
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

    public void onRender(final AbstractCard card, final SpriteBatch sb){
        if(trueCost <0 || trueCost == card.costForTurn || CardPatch.newVarField.eternity.get(card))
            return;
        Color trueCostColor = CardHelper.getColor(255,186,255).cpy();
        trueCostColor.a=card.transparency;
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale*0.8f);
        BitmapFont font= FontHelper.cardEnergyFont_L;
        String costText = Integer.toString(this.trueCost);
        FontHelper.renderRotatedText(sb, font, costText, card.current_x, card.current_y, -105.0f * card.drawScale * Settings.scale, 205.0f * card.drawScale * Settings.scale, card.angle, false, trueCostColor);
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

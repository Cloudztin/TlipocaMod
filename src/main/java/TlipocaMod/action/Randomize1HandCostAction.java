package TlipocaMod.action;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class Randomize1HandCostAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("Randomize1HandCostAction").TEXT;
    private final AbstractPlayer p;
    List<AbstractCard> cannotChange= new ArrayList<>();

    public Randomize1HandCostAction(AbstractPlayer p) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            int avai=0;
            for(AbstractCard c : p.hand.group)
                if(c.cost>=0) avai++;

            if(avai==0){
                this.isDone=true;

                return;
            }

            if (avai == 1) {
                for(AbstractCard c : p.hand.group)
                    if(c.cost>=0){
                        int newCost = AbstractDungeon.cardRandomRng.random(3);
                        if(c.cost!=newCost)
                            c.isCostModified = true;
                        c.cost = newCost;
                        c.costForTurn = newCost;
                        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, CostForTurnModifier.ID);
                        if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                            if(((CostForTurnModifier) mods.get(0)).trueCost>=0)
                                ((CostForTurnModifier) mods.get(0)).trueCost = newCost;
                        break;
                    }
                this.isDone=true;

                return;
            }

            if(avai>1){


                for(AbstractCard c : p.hand.group)
                    if(c.cost<0) cannotChange.add(c);

                p.hand.group.removeAll(cannotChange);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0],1,false, false, false, false);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();

            }

        }else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if(c.cost!=newCost)
                    c.isCostModified = true;
                c.cost = newCost;
                c.costForTurn = newCost;
                ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, CostForTurnModifier.ID);
                if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                    if(((CostForTurnModifier) mods.get(0)).trueCost>=0)
                        ((CostForTurnModifier) mods.get(0)).trueCost = newCost;

                c.applyPowers();
                this.p.hand.addToTop(c);
                c.flash(Color.WHITE.cpy());
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;

        }


    }


    private void returnCards(){
        for(AbstractCard c:this.cannotChange)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}

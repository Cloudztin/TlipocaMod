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
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;
import java.util.List;

public class ChangeCostAction extends AbstractGameAction {


    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ChangeCostAction").TEXT;
    private final AbstractPlayer p;
    private final int aimCost;
    List<AbstractCard> cannotChange= new ArrayList<>();

    public ChangeCostAction(AbstractPlayer p, int aimCost) {
        this.p = p;
        this.aimCost = aimCost;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            int avai=0;
            for(AbstractCard c : p.hand.group)
                if(c.cost>=0) avai++;

            if(avai==0){
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[2], true));
                this.isDone=true;

                return;
            }
            if(avai>=1){


                for(AbstractCard c : p.hand.group)
                    if(c.cost<0) cannotChange.add(c);

                p.hand.group.removeAll(cannotChange);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0]+this.aimCost+TEXT[1],1,false, true, false, false);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();

            }

        }else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                c.flash();
                int newCost = aimCost;
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

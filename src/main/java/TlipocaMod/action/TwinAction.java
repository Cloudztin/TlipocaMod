package TlipocaMod.action;

import TlipocaMod.TlipocaMod.TlipocaModifier;
import TlipocaMod.patches.CardPatch;
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

public class TwinAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("TwinAction").TEXT;
    private List<AbstractCard> cannotChange=new ArrayList<>();
    private AbstractPlayer p;


    public TwinAction() {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }
            if(this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();
                if(!CardPatch.newVarField.twinCast.get(c)){
                    if(c.cost>=0)
                        c.updateCost(1);
                    CardModifierManager.addModifier(c, new TlipocaModifier(TlipocaModifier.supportedModify.TWINCAST, false));
                    c.superFlash(Color.RED.cpy());
                }
                this.isDone = true;
                return;
            }

            for(AbstractCard c : this.p.hand.group)
                if(CardPatch.newVarField.twinCast.get(c))
                    this.cannotChange.add(c);

            this.p.hand.group.removeAll(this.cannotChange);

            if(p.hand.isEmpty()){
                returnCards();
                this.isDone = true;
                return;
            }
            if(p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();
                if(c.cost>=0)
                    c.updateCost(1);
                if(!CardPatch.newVarField.twinCast.get(c))
                    CardModifierManager.addModifier(c, new TlipocaModifier(TlipocaModifier.supportedModify.TWINCAST, false));
                c.superFlash(Color.RED.cpy());
                returnCards();
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[1],1,false, false, false, false);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;

        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                if(c.cost>=0)
                    c.updateCost(1);
                if(!CardPatch.newVarField.twinCast.get(c))
                    CardModifierManager.addModifier(c, new TlipocaModifier(TlipocaModifier.supportedModify.TWINCAST, false));
                c.applyPowers();
                this.p.hand.addToTop(c);
                c.superFlash(Color.RED.cpy());
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
            tickDuration();
        }

    }

    private void returnCards(){
        for(AbstractCard c:this.cannotChange)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}

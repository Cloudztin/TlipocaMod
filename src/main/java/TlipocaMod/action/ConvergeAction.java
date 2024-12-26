package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
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

public class ConvergeAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("ConvergeAction").TEXT;
    private final List<AbstractCard> cannotChange=new ArrayList<>();
    private final AbstractPlayer p= AbstractDungeon.player;

    public ConvergeAction() {
        duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if(this.duration== Settings.ACTION_DUR_MED){
            boolean src=false;
            int aim=0;

            for(AbstractCard c: p.hand.group){
                if(CardPatch.newVarField.lockNUM.get(c)>0)
                    src=true;
                if(CardPatch.newVarField.canLock.get(c))
                    aim++;
            }
            if(!src || aim<2){

                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
                isDone=true;
                return;
            }

            for(AbstractCard c: p.hand.group)
                if(!CardPatch.newVarField.canLock.get(c))
                    cannotChange.add(c);

            p.hand.group.removeAll(cannotChange);

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1,false, false, false, false);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            AbstractCard c=AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
            int layer=0;
            for(AbstractCard c2: p.hand.group)
                if(CardPatch.newVarField.lockNUM.get(c2)>0){
                    layer+=CardPatch.newVarField.lockNUM.get(c2);
                    CardPatch.zeroLock(c2);
                }
            CardPatch.lockNumUp(c, layer);
            c.applyPowers();
            this.p.hand.addToTop(c);
            c.superFlash(Color.WHITE.cpy());

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

package TlipocaMod.action;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.List;

public class DrawHightestCostAction extends AbstractGameAction {

    private AbstractPlayer p;

    public DrawHightestCostAction(AbstractPlayer p) {
        this.p = p;
    }

    public void update() {
        int aim=-1;
        for(AbstractCard c : p.drawPile.group) {
            if(c.cost>=0 && c.costForTurn>aim) aim=c.costForTurn;
            if(c.cost==-1 && EnergyPanel.totalCount>=aim) aim=EnergyPanel.totalCount;
        }

        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        if(aim>-1)
            for(AbstractCard c : p.drawPile.group) {
                if(c.cost>=0 && c.costForTurn==aim) tmp.addToTop(c);
                if(c.cost==-1 && EnergyPanel.totalCount==aim) tmp.addToTop(c);
            }
        else
            for(AbstractCard c : p.drawPile.group)
                if(c.cost==-2) tmp.addToTop(c);



        if(!tmp.isEmpty()) {
            AbstractCard c= tmp.getRandomCard(AbstractDungeon.cardRandomRng);

            if(this.p.hand.size() == BaseMod.MAX_HAND_SIZE){
                this.p.drawPile.moveToDiscardPile(c);
                this.p.createHandIsFullDialog();
            }
            else
                this.p.drawPile.moveToHand(c);
        }

        this.isDone = true;
    }
}

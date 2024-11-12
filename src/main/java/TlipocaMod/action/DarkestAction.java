package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.swing.text.AbstractDocument;

public class DarkestAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final int upAmt;

    public DarkestAction(int upAmt) {
        this.p= AbstractDungeon.player;
        this.upAmt=upAmt;
    }

    public void update() {
        int size=this.p.hand.size();
        for(AbstractCard c: this.p.drawPile.group)
            if(c.cost>=0) c.updateCost(this.upAmt);

        for(AbstractCard c: this.p.hand.group)
            if(c.cost>=0) c.updateCost(this.upAmt);

        for(AbstractCard c: this.p.discardPile.group)
            if(c.cost>=0) c.updateCost(this.upAmt);


        this.isDone = true;
    }
}

package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;

public class RecoverAction extends AbstractGameAction {

    private List<AbstractCard> tar;
    private int bias;


    public RecoverAction( List<AbstractCard> tar, int bias ) {
        this.tar = tar;
        this.bias = bias;
    }

    public void update() {

        for(AbstractCard c: AbstractDungeon.player.drawPile.group)
            if(this.tar.contains(c)){
                c.updateCost(-bias);
                this.tar.remove(c);
                c.isCostModified=false;
            }

        for(AbstractCard c: AbstractDungeon.player.hand.group)
            if(this.tar.contains(c)){
                c.updateCost(-bias);
                this.tar.remove(c);
                c.isCostModified=false;
            }

        for(AbstractCard c: AbstractDungeon.player.discardPile.group)
            if(this.tar.contains(c)){
                c.updateCost(-bias);
                this.tar.remove(c);
                c.isCostModified=false;
            }

        this.isDone=true;
    }
}

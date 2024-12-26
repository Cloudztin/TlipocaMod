package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class ConcealAction extends AbstractGameAction {

    private final int block;
    private final AbstractPlayer p;

    public ConcealAction(AbstractPlayer p, int block) {
        this.p = p;
        this.block = block;
    }

    public void update() {
        if(p.hand.isEmpty()) {
            this.isDone = true;
            return;
        }

        for(AbstractCard c : p.hand.group)
            if(c.type== AbstractCard.CardType.ATTACK && CardPatch.newVarField.lockNUM.get(c)==0 && CardPatch.newVarField.canLock.get(c)){
                CardPatch.intoLock(c, 1);
                addToTop(new GainBlockAction(p, block));
            }

        this.isDone=true;
    }
}

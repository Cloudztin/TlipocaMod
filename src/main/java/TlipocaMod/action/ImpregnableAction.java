package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ImpregnableAction extends AbstractGameAction {

    private final AbstractCard card;

    public ImpregnableAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group)
            if (CardPatch.newVarField.canLock.get(c)) CardPatch.lockNumUp(c, 1);
        int sum = 0;
        for (AbstractCard c : p.hand.group)
            sum += CardPatch.newVarField.lockNUM.get(c);

        if (sum > 0)
            card.baseBlock = sum * this.card.magicNumber;
        addToBot(new GainBlockAction(p, p, card.block));
        this.isDone = true;
    }
}

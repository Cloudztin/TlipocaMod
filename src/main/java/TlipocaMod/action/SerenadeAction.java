package TlipocaMod.action;

import TlipocaMod.TlipocaMod.TlipocaModifier;
import TlipocaMod.patches.CardPatch;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class SerenadeAction extends AbstractGameAction {

    private AbstractPlayer p;

    public SerenadeAction(AbstractPlayer p) {
        this.p = p;
    }

    public void update() {
        for(AbstractCard c: p.hand.group) if (c.cost >= 0){
            if(!CardPatch.newVarField.ephemeral.get(c))
                CardModifierManager.addModifier(c, new TlipocaModifier(TlipocaModifier.supportedModify.EPHEMERAL, true));
            c.setCostForTurn(0);
        }
        isDone = true;
    }
}

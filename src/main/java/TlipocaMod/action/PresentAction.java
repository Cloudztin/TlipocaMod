package TlipocaMod.action;

import TlipocaMod.TlipocaMod.TlipocaModifier;
import TlipocaMod.patches.CardPatch;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;

public class PresentAction extends AbstractGameAction {

    private AbstractPlayer p;

    public PresentAction(AbstractPlayer p) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {

        for(AbstractCard c : p.hand.group)
            if(!CardPatch.newVarField.eternity.get(c))
                CardModifierManager.addModifier(c, new TlipocaModifier( TlipocaModifier.supportedModify.ETERNITY, false));

        this.isDone=true;
    }
}

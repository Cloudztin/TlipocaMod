package TlipocaMod.action;

import TlipocaMod.TlipocaMod.TlipocaModifier;
import TlipocaMod.cards.uncommon.tlMystery;
import TlipocaMod.patches.CardPatch;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.TextureArrayData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import javax.swing.*;

public class EchoAction extends AbstractGameAction {

    private AbstractPlayer p;

    public EchoAction(AbstractPlayer p) {
        this.p = p;
    }

    public void update() {

        for(AbstractCard c : p.hand.group) if(!CardPatch.newVarField.resonate.get(c) && c.cost>=0)
            CardModifierManager.addModifier(c, new TlipocaModifier(TlipocaModifier.supportedModify.RESONATE, false));

        this.isDone=true;
    }
}

package TlipocaMod.action;

import TlipocaMod.TlipocaMod.TlipocaModifier;
import TlipocaMod.patches.CardPatch;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WhisperFollowAction extends AbstractGameAction {

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));

        tickDuration();

        if (this.isDone)
            for (AbstractCard c : DrawCardAction.drawnCards)
                if(!CardPatch.newVarField.resonate.get(c))
                    CardModifierManager.addModifier(c, new TlipocaModifier(TlipocaModifier.supportedModify.RESONATE, false));

    }
}

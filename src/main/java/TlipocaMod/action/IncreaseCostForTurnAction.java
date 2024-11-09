package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class IncreaseCostForTurnAction extends AbstractGameAction {
    private AbstractCard targetCard;

    public IncreaseCostForTurnAction(AbstractCard card, int amt) {
        this.targetCard = card;
        this.amount = amt;
        this.startDuration = Settings.ACTION_DUR_FASTER;
        this.duration = this.startDuration;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.targetCard.setCostForTurn(this.targetCard.costForTurn + this.amount);
        }

        this.tickDuration();
        if (Settings.FAST_MODE) {
            this.isDone = true;
        }
    }
}

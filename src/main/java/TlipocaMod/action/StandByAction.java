package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class StandByAction extends AbstractGameAction {

    public StandByAction(float duration) {
        setValues(null, null, 0);
        this.duration = duration;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        tickDuration();
    }
}

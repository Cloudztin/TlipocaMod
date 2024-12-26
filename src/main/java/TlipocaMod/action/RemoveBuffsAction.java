package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Objects;

public class RemoveBuffsAction extends AbstractGameAction {

    private final AbstractCreature c;

    public RemoveBuffsAction(AbstractCreature c) {
        this.c = c;
    }

    public void update() {

        for (AbstractPower p : this.c.powers)
            if (p.type == AbstractPower.PowerType.BUFF && !Objects.equals(p.ID, "Time Warp") && !Objects.equals(p.ID, "Invincible"))
                addToTop(new RemoveSpecificPowerAction(this.c, this.c, p.ID));

        this.isDone = true;
    }
}

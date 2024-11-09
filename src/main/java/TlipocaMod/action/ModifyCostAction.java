package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class ModifyCostAction extends AbstractGameAction {

    private final UUID uuid;
    private final int cost;

    public ModifyCostAction(final UUID uuid, final int cost) {
        this.uuid = uuid;
        this.cost = cost;
    }

    public void update() {
        if(this.uuid!=null)
            for (final AbstractCard c : GetAllInBattleInstances.get(this.uuid))
                if(c.cost >=0)
                    c.updateCost(this.cost);
        this.isDone = true;
    }
}

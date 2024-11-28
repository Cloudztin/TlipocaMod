package TlipocaMod.action;

import TlipocaMod.powers.FirstStrikePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class HumanityAction extends AbstractGameAction {

    private boolean freeToPlayOnce = false;
    private int energyOnUse = -1;
    private final AbstractPlayer p;



    public HumanityAction(AbstractPlayer p,  int amount, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.amount = amount;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }


    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            for (int i = 0; i < effect; i++) addToBot(new GainBlockAction(this.p, this.p, this.amount));
            addToBot(new ApplyPowerAction(p, p, new FirstStrikePower(p, effect)));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}

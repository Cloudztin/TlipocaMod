package TlipocaMod.action;

import TlipocaMod.powers.MaintenancePower;
import TlipocaMod.powers.UpgradedMaintenancePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MaintenanceAction extends AbstractGameAction {

    private boolean freeToPlayOnce = false;
    private final boolean upgraded;

    public MaintenanceAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }
    private AbstractPlayer p;
    private int energyOnUse;

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
            if(!this.upgraded)
                addToBot(new ApplyPowerAction(this.p, this.p, new MaintenancePower(this.p, effect)));
            else
                addToBot(new ApplyPowerAction(this.p, this.p, new UpgradedMaintenancePower(this.p, effect)));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}

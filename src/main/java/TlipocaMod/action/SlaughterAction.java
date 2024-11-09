package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SlaughterAction extends AbstractGameAction {

    private final AbstractCard card;
    private boolean freeToPlayOnce = false;
    private int energyOnUse = -1;

    private final AbstractPlayer p;

    public SlaughterAction(AbstractPlayer p, AbstractCard card, boolean freeToPlayOnce, int energyOnUse){
        this.card = card;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        this.actionType = ActionType.DAMAGE;
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
            for (int i = 0; i < effect; i++) addToBot(new AttackDamageRandomEnemyAction(card, AttackEffect.BLUNT_HEAVY));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}

package TlipocaMod.action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class TreasureAction extends AbstractGameAction {

    private final DamageInfo info;
    private final UUID uuid;
    int amount;

    public TreasureAction(AbstractCreature target, DamageInfo info, int amount, UUID targetUUID) {
        this.info = info;
        setValues(target, info);
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.uuid = targetUUID;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {


                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid.equals(this.uuid)) {
                        if (c.misc < 3)
                            c.misc += this.amount;
                        c.updateCost(-this.amount);
                    }
                }

                for (final AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                    if (c.misc < 3)
                        c.misc += this.amount;
                    c.updateCost(-this.amount);
                    c.flash(Color.WHITE.cpy());
                }
            }


            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        tickDuration();
    }
}

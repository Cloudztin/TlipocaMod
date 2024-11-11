package TlipocaMod.action;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Bludgeon;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import java.util.UUID;

public class JusticeAction extends AbstractGameAction {

    private DamageInfo info;
    private UUID uuid;
    int amount;

    public JusticeAction(final AbstractCreature target, final DamageInfo info, final int amount, final UUID targetUUID) {
        this.setValues(target, this.info = info);
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.8F;
        this.uuid = targetUUID;
    }

    public void update() {
        if (this.duration == 0.8F && this.target != null) {
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")){
                for (final AbstractCard c : AbstractDungeon.player.masterDeck.group){
                    if (c.uuid.equals(this.uuid)){
                        if (c.misc < 4){
                            final AbstractCard abstractCard = c;
                            abstractCard.misc += this.amount;
                        }
                        c.updateCost(-this.amount);
                    }

                }

            }
            for (final AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                if (c.misc < 3) {
                    final AbstractCard abstractCard2 = c;
                    abstractCard2.misc += this.amount;
                }
                c.updateCost(-this.amount);
                c.flash(Color.WHITE.cpy());
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

        }
        tickDuration();

    }
}




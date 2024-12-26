package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FlickAction extends AbstractGameAction {
    public FlickAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    private final DamageInfo info;

    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;

            return;
        }
        tickDuration();

        if (this.isDone) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY, false));

            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0) {
                if (!this.target.hasPower("Artifact")) {
                    addToTop(new ApplyPowerAction(this.target, this.source, new GainStrengthPower(this.target, this.target.lastDamageTaken), this.target.lastDamageTaken, true, AbstractGameAction.AttackEffect.NONE));
                }
                addToTop(new ApplyPowerAction(this.target, this.source, new StrengthPower(this.target, -this.target.lastDamageTaken), -this.target.lastDamageTaken, true, AbstractGameAction.AttackEffect.NONE));
                if (this.target.hb != null) {
                    addToTop(new VFXAction(new WallopEffect(this.target.lastDamageTaken, this.target.hb.cX, this.target.hb.cY)));
                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                addToTop(new WaitAction(0.1F));
            }
        }
    }
}

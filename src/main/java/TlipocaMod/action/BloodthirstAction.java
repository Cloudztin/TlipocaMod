package TlipocaMod.action;

import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BloodthirstAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final DamageInfo info;

    public BloodthirstAction(AbstractPlayer p, AbstractMonster m, DamageInfo info) {
        this.p = p;
        this.m = m;
        this.info = info;
    }

    public void update() {
        if(this.m!=null) {
            int healAmount = 0;
            if(this.m.hasPower(BleedingPower.ID))
                healAmount = this.m.getPower(BleedingPower.ID).amount;


            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.m.hb.cX, this.m.hb.cY, AttackEffect.SHIELD, false));
            this.m.damage(this.info);

            if (this.m.lastDamageTaken > 0 && this.m.currentHealth > 0 && healAmount > 0)
                this.p.heal(healAmount);
        }


        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();



        this.isDone = true;

    }

}

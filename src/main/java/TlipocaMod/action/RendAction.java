package TlipocaMod.action;

import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RendAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final DamageInfo info;
    private final int amount;

    public RendAction(AbstractPlayer p, AbstractMonster m, DamageInfo info, int amount) {
        this.p = p;
        this.m = m;
        this.info = info;
        this.amount = amount;
    }

    public void update() {

        if(m!=null){

            AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AttackEffect.SLASH_VERTICAL, false));
            this.m.damage(this.info);

            if(this.m.lastDamageTaken >0){
                if(!this.m.isDead && !this.m.isDying){
                    if(this.m.hasPower(BleedingPower.ID))
                        addToTop(new ApplyPowerAction(this.m, this.p, new VulnerablePower(this.m, this.amount, false)));
                    addToTop(new ApplyPowerAction(this.m, this.p, new BleedingPower(this.m, this.amount)));
                }
            }


        }

        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();

        this.isDone = true;
    }


}

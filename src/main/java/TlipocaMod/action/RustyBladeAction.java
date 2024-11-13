package TlipocaMod.action;

import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RustyBladeAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final DamageInfo info;
    private final int bleedAmt;

    public RustyBladeAction(AbstractPlayer p, AbstractMonster m, DamageInfo info, int bleedAmt) {
        this.p = p;
        this.m = m;
        this.info = info;
        this.bleedAmt = bleedAmt;
    }

    public void update() {
        if(this.m!=null){
            boolean hasBlock= this.m.currentBlock > 0;

            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.m.hb.cX, this.m.hb.cY, AttackEffect.SLASH_DIAGONAL, false));
            this.m.damage(this.info);

            if(this.m.lastDamageTaken>0 && hasBlock)
                addToTop(new ApplyPowerAction(this.m, this.p, new BleedingPower(this.m, this.bleedAmt)));

        }

        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();



        this.isDone = true;

    }
}

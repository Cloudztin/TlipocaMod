package TlipocaMod.action;

import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class HeartPierceAction extends AbstractGameAction {

    public final AbstractPlayer p;
    public final AbstractMonster m;
    private final DamageInfo info;


    public HeartPierceAction(AbstractPlayer p, AbstractMonster m, DamageInfo info) {
        this.p = p;
        this.m = m;
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
        if(m!=null){
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.m.hb.cX, this.m.hb.cY, AttackEffect.SLASH_HEAVY, false));
            this.m.damage(this.info);
            if(m.lastDamageTaken>0)
                addToTop(new ApplyPowerAction(this.m, this.p, new BleedingPower(this.m, this.m.lastDamageTaken)));
        }

        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions();
        else
            addToTop( new WaitAction(0.1F));

        this.isDone=true;
    }

}

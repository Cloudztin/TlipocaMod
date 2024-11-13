package TlipocaMod.action;

import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AfireAction extends AbstractGameAction {

    private final DamageInfo info;
    public AfireAction(AbstractCreature target, DamageInfo info){
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.target = target;
        this.info = info;
    }

    public void update(){
        if(this.target!=null && this.target.hasPower(BleedingPower.ID)){
            addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            addToTop(new GainEnergyAction(1));
        }

        addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
        this.isDone = true;
    }
}

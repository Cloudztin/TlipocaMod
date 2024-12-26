package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class FragilePower extends AbstractTlipocaPower{

    private static final String powerName="Fragile";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.DEBUFF;




    public FragilePower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount , type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
            description=DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }



    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0){
            if(this.amount ==1)
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            if(this.amount>1)
                addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        return damageAmount;
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type){
        if(type == DamageInfo.DamageType.NORMAL)
            return damage*2.0f;
        return damage;
    }

}

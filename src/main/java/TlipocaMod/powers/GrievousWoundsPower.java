package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class GrievousWoundsPower extends AbstractTlipocaPower{
    private static final String powerName="GrievousWounds";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;




    public GrievousWoundsPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner,amount , type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL){
            flash();
            addToTop(new ApplyPowerAction(target, this.owner, new BleedingPower(target, this.amount)));
        }
    }
}

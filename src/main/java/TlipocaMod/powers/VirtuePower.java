package TlipocaMod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class VirtuePower extends AbstractTlipocaPower implements OnReceivePowerPower {

    private static final String powerName="Virtue";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.BUFF;


    public VirtuePower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1)
            description=DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] +this.amount+DESCRIPTIONS[2];
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(abstractPower.type==PowerType.DEBUFF){
            if(this.amount==1)
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            else  addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
            return false;
        }
        return false;
    }
}

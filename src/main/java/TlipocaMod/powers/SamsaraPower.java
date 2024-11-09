package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class SamsaraPower extends AbstractTlipocaPower{

    private static final String powerName="Samsara";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.DEBUFF;




    public SamsaraPower(AbstractCreature owner) {
        super(NAME, ID, owner,-1 , type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
        addToTop(new ApplyPowerAction(this.owner, this.owner, new BrokenTimePower(this.owner, 1), 1));
    }
}

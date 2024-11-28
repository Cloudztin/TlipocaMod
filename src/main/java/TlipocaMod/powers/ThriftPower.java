package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class ThriftPower extends AbstractTlipocaPower{

    private static final String powerName="Thrift";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF ;




    public ThriftPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner,amount , type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer && EnergyPanel.totalCount > 0)
            addToBot(new GainBlockAction(this.owner, this.owner,this.amount*EnergyPanel.totalCount));

    }

}

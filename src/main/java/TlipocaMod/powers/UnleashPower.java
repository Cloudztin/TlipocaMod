package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class UnleashPower extends AbstractTlipocaPower {

    private static final String powerName="Unleash";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;
    private static final int limit=3;

    private int eP1=0;
    private int loseAmt=0;

    public UnleashPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        this.amount2=limit;
        updateDescription();
    }


    @Override
    public void updateDescription() {
        if(this.amount == 1) description = DESCRIPTIONS[0] +this.amount+DESCRIPTIONS[1]+this.amount2+DESCRIPTIONS[3];
        else description = DESCRIPTIONS[0] +this.amount+DESCRIPTIONS[2]+this.amount2+DESCRIPTIONS[3];
    }


    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }


    @Override
    public void update(int slot) {
        super.update(slot);
        int eP = EnergyPanel.getCurrentEnergy();
        if(eP1>eP){
            loseAmt+=(eP1-eP);
            if(loseAmt>= limit){
                addToTop(new DrawCardAction((loseAmt/ limit)*this.amount));
                loseAmt%= limit;
            }
            this.amount2= limit -loseAmt;
            updateDescription();
        }
        eP1= eP;
    }

}

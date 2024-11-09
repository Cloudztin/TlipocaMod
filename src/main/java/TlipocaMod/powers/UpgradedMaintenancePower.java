package TlipocaMod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class UpgradedMaintenancePower extends AbstractTlipocaPower{

    private static final String powerName="UpMaintenance";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;




    public UpgradedMaintenancePower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner,amount , type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new GainEnergyAction(1));
        addToBot(new ExpertiseAction(AbstractDungeon.player, BaseMod.MAX_HAND_SIZE));
        if(this.amount <=1)
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        else
            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
    }
}

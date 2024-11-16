package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class ContagionPower extends AbstractTlipocaPower{

    private static final String powerName="Contagion";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.BUFF;


    public ContagionPower(AbstractCreature owner,  int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+ this.amount + DESCRIPTIONS[1];
    }


    public void atStartOfTurn() {
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters)
            addToBot(new ApplyPowerAction(mo, this.owner, new BleedingPower(mo, this.amount)));
    }
}

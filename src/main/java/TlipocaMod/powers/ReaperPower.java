package TlipocaMod.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class ReaperPower extends AbstractTlipocaPower {

    private static final String powerName="Reaper";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;




    public ReaperPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] +this.amount+DESCRIPTIONS[1];
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if(!m.halfDead && !m.hasPower("Minion")){
            this.owner.increaseMaxHp(this.amount, true);
        }
    }

}

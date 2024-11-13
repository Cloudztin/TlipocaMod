package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class AllEnemiesLoseStrengthThisTurnAction extends AbstractGameAction {

    public AllEnemiesLoseStrengthThisTurnAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        if(this.amount <= 0){
            this.isDone = true;

            return;
        }
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            if(!mo.hasPower(ArtifactPower.POWER_ID))
                addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new GainStrengthPower(mo, this.amount)));
            addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, -this.amount)));
        }
        this.isDone = true;
    }
}

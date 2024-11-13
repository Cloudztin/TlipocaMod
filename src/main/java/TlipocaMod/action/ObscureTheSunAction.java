package TlipocaMod.action;

import TlipocaMod.powers.BleedingPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class ObscureTheSunAction extends AbstractGameAction {

    private boolean freeToPlayOnce = false;
    private int energyOnUse = -1;
    private boolean upgraded;
    private final AbstractPlayer p;



    public ObscureTheSunAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }


    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if(this.upgraded)
            effect ++;


        if (effect > 0) {
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLACK, true));
            for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
                addToBot(new ApplyPowerAction(mo, this.p, new WeakPower(mo, effect, false)));
                addToBot(new ApplyPowerAction(mo, this.p, new VulnerablePower(mo, effect, false)));
                addToBot(new ApplyPowerAction(mo, this.p, new BleedingPower(mo, effect)));
            }
            addToBot(new AllEnemiesLoseStrengthThisTurnAction(effect));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}

package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.action.IncreaseCostForTurnAction;
import TlipocaMod.action.IncreaseCostForTurnOnceAction;
import TlipocaMod.action.IncreaseHandCostAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class NightCrown extends AbstractTlipocaRelic {

    public static final String relicName = "NightCrown";
    public static final String ID = TlipocaMod.getID(relicName);

    public NightCrown() {
        super(relicName, RelicTier.BOSS, LandingSound.CLINK, true);
    }


    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    public void atTurnStartPostDraw() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new IncreaseHandCostAction(1, false, 3));
    }



    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NightCrown();
    }
}

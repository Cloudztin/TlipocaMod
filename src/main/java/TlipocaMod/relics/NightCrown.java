package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.action.IncreaseHandCostAction;
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
        addToTop(new IncreaseHandCostAction(1, false, 1));
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

package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.cards.tempCards.hllFetter;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ShackleOfDream extends AbstractTlipocaRelic{

    public static final String relicName = "ShackleOfDream";
    public static final String ID = TlipocaMod.getID(relicName);

    public ShackleOfDream() {
        super(relicName, RelicTier.BOSS, LandingSound.MAGICAL, true);

    }


    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public void onPlayerEndTurn() {
        addToBot(new MakeTempCardInDiscardAction(new hllFetter(), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ShackleOfDream();
    }

}


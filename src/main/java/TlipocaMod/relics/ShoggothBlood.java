package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import basemod.BaseMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ShoggothBlood extends AbstractTlipocaRelic{

    public static final String relicName = "ShoggothBlood";
    public static final String ID = TlipocaMod.getID(relicName);

    public ShoggothBlood() {
        super(relicName, RelicTier.BOSS, LandingSound.FLAT, true);

    }

    public void onEquip() {
        BaseMod.MAX_HAND_SIZE -= 2;
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip() {
        BaseMod.MAX_HAND_SIZE += 2;
        AbstractDungeon.player.energy.energyMaster--;
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ShoggothBlood();
    }

}

package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DragonBlood extends AbstractTlipocaRelic{

    public static final String relicName = "DragonBlood";
    public static final String ID = TlipocaMod.getID(relicName);

    public DragonBlood() {
        super(relicName, RelicTier.UNCOMMON, LandingSound.MAGICAL, true);
    }


    public void atBattleStart() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, new BleedingPower(null, 2), 2, true));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DragonBlood();
    }
}

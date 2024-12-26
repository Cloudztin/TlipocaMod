package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class TangKnife extends AbstractTlipocaRelic{

    public static final String relicName = "TangKnife";
    public static final String ID = TlipocaMod.getID(relicName);

    public TangKnife() {
        super(relicName, RelicTier.STARTER, LandingSound.CLINK, true);

    }

    public void onMonsterDeath(AbstractMonster m) {
        flash();
        addToBot(new RelicAboveCreatureAction(m, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TangKnife();
    }

    public boolean canSpawn() {
        return false;
    }
}

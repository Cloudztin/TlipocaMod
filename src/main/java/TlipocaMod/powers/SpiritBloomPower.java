package TlipocaMod.powers;

import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class SpiritBloomPower extends AbstractTlipocaPower{

    private static final String powerName="SpiritBloom";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;

    public SpiritBloomPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount , type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        if(amount > 1)
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public void onUnlock(AbstractCard c) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            for (AbstractCard card : AbstractDungeon.player.hand.group)
                if (CardPatch.newVarField.lockNUM.get(card) > 0) CardPatch.lockNumDes(card, 1);
    }
}

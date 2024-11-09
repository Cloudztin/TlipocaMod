package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class DarkPower extends AbstractTlipocaPower{

    private static final String powerName="Dark";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.BUFF;


    public DarkPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        for(int i=0; i<amount; i++)
            description += DESCRIPTIONS[1];
        description += DESCRIPTIONS[2];
    }

    public void onCardDraw(AbstractCard card) {
        addToBot(new GainEnergyAction(this.amount));
    }
}

package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class FlowPower extends AbstractTlipocaPower{

    private static final String powerName="Flow";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;


    public FlowPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+ amount+DESCRIPTIONS[1];
    }

    @Override
    public void onNumUp(AbstractCard c, int n) {
        addToBot(new GainBlockAction(this.owner, this.amount * n));
    }

    @Override
    public void onNumDes(AbstractCard c, int n) {
        addToBot(new GainBlockAction(this.owner, this.amount * n));
    }
}

package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class RetracePower extends AbstractTlipocaPower{


    private static final String powerName="Retrace";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;

    private boolean justEvoked;


    public RetracePower(AbstractCreature owner, int amount, boolean justEvoked) {
        super(NAME, ID, owner, amount, type);
        this.justEvoked = justEvoked;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(amount == 1)
            description=DESCRIPTIONS[0];
        if(amount > 1)
            description = DESCRIPTIONS[1]+ amount+DESCRIPTIONS[2];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.justEvoked) {
            this.justEvoked = false;
            return;

        }


        if (!card.purgeOnUse && this.amount > 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= this.amount) {
            if (card.type != AbstractCard.CardType.POWER) {
                flash();
                addToBot(new DiscardToHandAction(card));
            }
        }
    }

}

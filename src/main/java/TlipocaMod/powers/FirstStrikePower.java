package TlipocaMod.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class FirstStrikePower extends AbstractTlipocaPower {

    private static final String powerName="FirstStrike";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.BUFF;




    public FirstStrikePower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = DESCRIPTIONS[0];
        if(amount>=2)
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public void atEndOfTurn(final boolean isPlayer) {
        this.flash();
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic("Runic Pyramid") && !AbstractDungeon.player.hasPower("Equilibrium")) {
            this.addToBot(new RetainCardsAction(this.owner, this.amount));
        }
        if (AbstractDungeon.player.hasPower(FirstStrikePower.ID)) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, FirstStrikePower.ID));
        }
    }
}

package TlipocaMod.powers;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class BrokenTimePower extends AbstractTlipocaPower {

    private static final String powerName="BrokenTime";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.DEBUFF;

    public final List<AbstractCard> cardsDone= new ArrayList<>();



    public BrokenTimePower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] +this.amount+DESCRIPTIONS[1];
    }

    @Override
    public void update(int slot){
        super.update(slot);
        for(AbstractCard c: AbstractDungeon.player.hand.group)
            if(c.cost>=0) if(!this.cardsDone.contains(c)){
                CardModifierManager.addModifier(c, new CostForTurnModifier(this.amount));
                cardsDone.add(c);
            }

        for(AbstractCard c: AbstractDungeon.player.drawPile.group)
            if(c.cost>=0) if(!this.cardsDone.contains(c)){
                CardModifierManager.addModifier(c, new CostForTurnModifier(this.amount));
                cardsDone.add(c);
            }

        for(AbstractCard c: AbstractDungeon.player.discardPile.group)
            if(c.cost>=0) if(!this.cardsDone.contains(c)){
                CardModifierManager.addModifier(c, new CostForTurnModifier(this.amount));
                cardsDone.add(c);
            }

    }


    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }

}

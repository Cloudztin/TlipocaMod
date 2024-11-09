package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class GodheadPower extends AbstractTlipocaPower{


    private static final String powerName="Godhead";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;




    public GodheadPower(AbstractCreature owner,int amount) {
        super(NAME, ID, owner, amount, type);

        int amt=0;
        for(AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisTurn) if((c.costForTurn>=2 && !c.freeToPlayOnce) || c.cost == -1 && c.energyOnUse >=2) amt++;
        this.amount2 = amt;
        updateDescription();
    }

    public void atStartOfTurn(){
        this.amount2 = 0;
        updateDescription();
    }

    public void onAfterCardPlayed(AbstractCard usedCard) {
        if((usedCard.costForTurn >= 2 && !usedCard.freeToPlayOnce) || (usedCard.cost == -1 && usedCard.energyOnUse >= 2)){
            if(this.amount2<this.amount){
                if(usedCard.costForTurn>=2) addToBot(new GainEnergyAction(usedCard.costForTurn));
                if(usedCard.cost== -1) addToBot(new GainEnergyAction(usedCard.energyOnUse));
            }
            this.amount2++;
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1)
            this.description = DESCRIPTIONS[0];
        else
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        if(this.amount2==0)
            this.description += DESCRIPTIONS[6];
        else if(this.amount2 ==1)
            this.description += DESCRIPTIONS[3];
        else
            this.description += (DESCRIPTIONS[4] + this.amount2 + DESCRIPTIONS[5]);
    }
}

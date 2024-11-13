package TlipocaMod.powers;



import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class HumanityPower extends AbstractTlipocaPower {

    private static final String powerName="Humanity";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.BUFF;




    public HumanityPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(this.amount == 1)
            description=DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] +this.amount+DESCRIPTIONS[2];
    }


    public void onUseCard(AbstractCard card, UseCardAction action){
        if ((card.cost>=0 && card.costForTurn==1) ||(card.cost==-1 && card.energyOnUse==1) ){
            flash();
            addToBot(new DrawCardAction(this.amount));
        }
    }


}
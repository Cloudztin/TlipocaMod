package TlipocaMod.powers;

import TlipocaMod.patches.CardPatch;
import TlipocaMod.relics.QuetzalcoatlsFeather;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class SheathedPower extends AbstractTlipocaPower{

    private static final String powerName="Sheathed";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.BUFF;




    public SheathedPower(AbstractCreature owner) {
        super(NAME, ID, owner, -1, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(AbstractDungeon.player.hasRelic(QuetzalcoatlsFeather.ID))
            description=DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[0];
    }


    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL) {
            if(AbstractDungeon.player.hasRelic(QuetzalcoatlsFeather.ID)) return damage + 5;
            return damage + 2;
        }
        return damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if(card.type== AbstractCard.CardType.ATTACK){
            flash();
            if(AbstractDungeon.player.hasRelic(QuetzalcoatlsFeather.ID)) addToBot(new DrawCardAction(1));
        }
        if(!CardPatch.newVarField.mastery.get(card) && !this.owner.hasPower(MasteryPower.ID))
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}

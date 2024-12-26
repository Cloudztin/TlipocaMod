package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class TripleDamagePower extends AbstractTlipocaPower{

    private static final String powerName="TripleDamage";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.BUFF;

    public TripleDamagePower(AbstractCreature owner, int amount) {
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

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type== AbstractCard.CardType.ATTACK)
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL)
            return damage*3.0f;
        return damage;
    }
}

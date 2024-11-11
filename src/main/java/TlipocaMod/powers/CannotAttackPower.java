package TlipocaMod.powers;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class CannotAttackPower extends AbstractTlipocaPower{

    private static final String powerName="CannotAttack";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.DEBUFF;




    public CannotAttackPower(AbstractCreature owner) {
        super(NAME, ID, owner, -1, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }


    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL) return -999;
        else return damage;
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL)
            return -999;
        else return damageAmount;
    }

    public void atEndOfTurn() {
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }
}

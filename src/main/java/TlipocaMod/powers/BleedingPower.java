package TlipocaMod.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class BleedingPower extends AbstractTlipocaPower implements HealthBarRenderPower {

    private static final String powerName="Bleed";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final PowerType type = PowerType.DEBUFF;




    public BleedingPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount , type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
    }

    public void atStartOfTurn() {
        this.flashWithoutSound();
        if(this.owner.hasPower(SacrificePower.ID))
            addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE));
        else
            addToTop(new LoseHPAction(this.owner, this.owner, this.amount, AbstractGameAction.AttackEffect.NONE));

        if(!AbstractDungeon.player.hasPower(CollapsePower.ID)){
            if(this.amount == 1)
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
            else addToBot(new ReducePowerAction(this.owner, this.owner, ID, (this.amount+1)/2 ));
        }
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL){
            flash();
            if(this.owner.hasPower(SacrificePower.ID))
                addToTop(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE));
            else
                addToTop(new LoseHPAction(this.owner, this.owner, this.amount, AbstractGameAction.AttackEffect.NONE));
            this.amount++;
            updateDescription();
        }
        return damageAmount;
    }

    public int getHealthBarAmount() {
        return this.amount;
    }

    public Color getColor() {
        return Color.PINK.cpy();
    }
}

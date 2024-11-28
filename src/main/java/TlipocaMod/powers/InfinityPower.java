package TlipocaMod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;
import static java.lang.Math.max;

public class InfinityPower extends AbstractTlipocaPower{

    private static final String powerName="Infinity";
    public static final String ID=getID(powerName);
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final AbstractPower.PowerType type = PowerType.BUFF;




    public InfinityPower(AbstractCreature owner, int amount) {
        super(NAME, ID, owner, amount, type);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        if(amount > 1)
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public void onRefreshHand() {
        if (AbstractDungeon.actionManager.actions.isEmpty() && AbstractDungeon.player.hand.size()<8 && !AbstractDungeon.actionManager.turnHasEnded  && !AbstractDungeon.player.hasPower("No Draw") && !AbstractDungeon.isScreenUp && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && (!AbstractDungeon.player.discardPile.isEmpty() || !AbstractDungeon.player.drawPile.isEmpty())) {
            this.flash();
            addToBot(new DrawCardAction(AbstractDungeon.player, max(8-AbstractDungeon.player.hand.size(),0)));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            if(amount == 1) addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
            if(amount>1) addToTop(new ReducePowerAction(owner, owner, ID, 1));
        }

    }
}

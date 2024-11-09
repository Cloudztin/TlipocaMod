package TlipocaMod.action;

import TlipocaMod.cards.rare.tlExecution;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ExecutionAction extends AbstractGameAction {
    private final AbstractCard c;
    private final AbstractMonster m;

    public ExecutionAction(tlExecution c, AbstractMonster m) {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.c = c;
        this.m = m;
        this.duration = 0.1F;
    }
    public void update() {
        if (this.duration == 0.1F && this.m != null){
            this.m.damage(new DamageInfo(AbstractDungeon.player, c.damage, c.damageTypeForTurn));
            if ((this.m.isDying || this.m.currentHealth <= 0) && !this.m.halfDead && !this.m.hasPower("Minion")){

                for (AbstractCard card : AbstractDungeon.player.masterDeck.group){
                    if(card.uuid.equals(this.c.uuid)){
                        final AbstractCard abstractCard=card;
                        abstractCard.misc++;
                        card.baseMagicNumber++;
                        card.magicNumber = card.baseMagicNumber;
                    }
                }

                for (final AbstractCard card : GetAllInBattleInstances.get(this.c.uuid)){
                    final AbstractCard abstractCard2=card;
                    abstractCard2.misc++;
                    card.baseMagicNumber++;
                    card.magicNumber = card.baseMagicNumber;
                    card.flash(Color.WHITE.cpy());

                }
            }

            this.isDone=true;
        }
    }
}

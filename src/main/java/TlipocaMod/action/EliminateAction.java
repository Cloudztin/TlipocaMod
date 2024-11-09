package TlipocaMod.action;

import TlipocaMod.cards.uncommon.tlEliminate;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EliminateAction extends AbstractGameAction {

    private final AbstractPlayer p;
    private final AbstractMonster m;
    private tlEliminate card;

    public EliminateAction(AbstractPlayer p, AbstractMonster m, tlEliminate card) {
        this.p = p;
        this.m = m;
        this.card = card;
    }

    public void update() {
        if(m!=null){
            this.m.damage(new DamageInfo(this.p, this.card.baseDamage, this.card.damageTypeForTurn));

            if((this.m.isDying || this.m.currentHealth <= 0) && !this.m.halfDead && !this.m.hasPower("Minion")){

                if(this.card.misc==0){
                    this.card.misc=1;
                    this.p.heal(this.card.magicNumber, true);
                }
            }
        }

        this.isDone=true;
    }
}

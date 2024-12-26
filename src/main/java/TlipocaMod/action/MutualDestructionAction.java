package TlipocaMod.action;

import TlipocaMod.powers.DrawLessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MutualDestructionAction extends AbstractGameAction {

    private final DamageInfo info;
    private final AbstractPlayer p;
    private final AbstractMonster tar;

    public MutualDestructionAction(DamageInfo info, AbstractPlayer p, AbstractMonster tar) {
        this.info = info;
        this.p = p;
        this.tar = tar;
    }

    public void update() {
        this.isDone = true;

        if(this.tar==null)
            return;

        this.tar.damage(info);
        if (this.tar.isDying || this.tar.currentHealth <= 0)
            return;

        addToBot(new ApplyPowerAction(p, this.tar, new DrawLessPower(p, 1)));
    }
}

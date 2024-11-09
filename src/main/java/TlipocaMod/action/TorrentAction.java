package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class TorrentAction extends AbstractGameAction {

    private AbstractPlayer p;
    private AbstractMonster m;
    private AbstractCard c;

    public TorrentAction(AbstractPlayer p, AbstractMonster m, AbstractCard c) {
        this.p = p;
        this.m = m;
        this.c = c;
    }

    public void update() {
        if(m!=null) for(int i=0; i< EnergyPanel.totalCount; i++)
            addToTop(new DamageAction(this.m, new DamageInfo(this.p, c.damage, c.damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
        this.isDone=true;
    }
}

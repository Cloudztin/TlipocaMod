package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WaveSplitterAction extends AbstractGameAction {

    private final DamageInfo info;
    private final AbstractCreature tar;

    public WaveSplitterAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.tar = target;
    }

    public void update() {
        for(AbstractCard c: AbstractDungeon.player.hand.group)
            if(CardPatch.newVarField.lockNUM.get(c)>0)
                addToTop(new DamageAction(tar, info, AttackEffect.SLASH_DIAGONAL));

        this.isDone=true;
    }
}

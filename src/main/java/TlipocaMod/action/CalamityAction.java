package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class CalamityAction extends AbstractGameAction {
    private final AbstractCard card;

    public CalamityAction(AbstractCard c) {
        this.card = c;
    }

    public void update() {
        boolean over = false;
        List<Integer> costList = new ArrayList<>();
        AbstractPlayer p = AbstractDungeon.player;


        for (AbstractCard c : p.hand.group) {
            if (CardPatch.newVarField.lockNUM.get(c) == 0 && CardPatch.newVarField.canLock.get(c)) {
                CardPatch.intoLock(c, 2);

                if (c.costForTurn != -2) {
                    if(costList.contains(c.costForTurn))
                        over = true;
                    else
                        costList.add(c.costForTurn);
                }
            }
        }

        if (!over) for (int i = 0; i < card.magicNumber; i++)
                addToBot( new AttackDamageRandomEnemyAction(this.card, AbstractGameAction.AttackEffect.LIGHTNING));

        this.isDone = true;
    }

}


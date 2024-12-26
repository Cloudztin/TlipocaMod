package TlipocaMod.action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BloodShakeAction extends AbstractGameAction {

    public void update() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type == AbstractCard.CardType.ATTACK)
            addToTop(new DrawCardAction(1, new AbstractGameAction() {
                @Override
                public void update() {
                    addToTop(new StandByAction(0.3F));

                    tickDuration();

                    if (isDone)
                        for (AbstractCard c : DrawCardAction.drawnCards)
                            if (c.canUpgrade()) {
                                c.upgrade();
                                c.superFlash(Color.WHITE.cpy());
                            }
                }
            }));

        this.isDone=true;
    }

}

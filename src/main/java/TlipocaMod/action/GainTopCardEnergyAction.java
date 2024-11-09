package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class GainTopCardEnergyAction extends AbstractGameAction {

    private final AbstractPlayer p;

    public GainTopCardEnergyAction(AbstractPlayer p) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if(this.p.drawPile.size() + this.p.discardPile.size() == 0) {
                this.isDone = true;

                return;
            }

            if(this.p.drawPile.isEmpty()) {
                addToTop(new GainTopCardEnergyAction(this.p));
                addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;

                return;
            }

            if(!this.p.drawPile.isEmpty()) {
                AbstractCard card = this.p.drawPile.getTopCard();

                if(card.cost>=0)
                    addToTop(new GainEnergyAction(card.costForTurn));

                if(card.cost==-1)
                    addToTop(new GainEnergyAction(EnergyPanel.totalCount));
            }

        }

        this.isDone=true;

    }
}

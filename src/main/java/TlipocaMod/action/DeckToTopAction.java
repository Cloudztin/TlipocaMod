package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DeckToTopAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("DeckToTop").TEXT;
    private final AbstractPlayer p;

    public DeckToTopAction() {
        this.p= AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()){
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if(this.p.drawPile.isEmpty()) {
                this.isDone=true;
                return;
            }
            if(this.p.drawPile.size() == 1) {
                this.isDone=true;
                return;
            }

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard c : AbstractDungeon.player.drawPile.group) tmpGroup.addToBottom(c);

            AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, TEXT[0], false, false, false, false);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.p.drawPile.moveToDeck(c, false);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        this.isDone = true;
        tickDuration();
    }

}

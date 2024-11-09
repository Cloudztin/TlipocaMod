package TlipocaMod.action;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AstrologyAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("AstrologyAction").TEXT;

    private int amt;
    private AbstractPlayer p;

    public AstrologyAction(AbstractPlayer p, int amt) {
        this.p = p;
        this.amt = amt;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || this.p.drawPile.isEmpty()){
            this.isDone = true;

            return;
        }
        if (this.duration == Settings.ACTION_DUR_FAST) {

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            this.amt=Math.min(this.p.drawPile.size(), this.amt);

            for(int i=0; i<this.amt; i++)
                tmpGroup.addToBottom(this.p.drawPile.group.get(i));

            AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, false, TEXT[0]);
        }
        else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    this.p.drawPile.moveToDiscardPile(c);
                    this.p.createHandIsFullDialog();
                    continue;
                }
                this.p.drawPile.moveToHand(c, this.p.drawPile);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();

        }

        tickDuration();
    }
}

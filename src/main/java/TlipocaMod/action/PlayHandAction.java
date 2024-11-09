package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayHandAction extends AbstractGameAction {

    private boolean exhaustCards;
    private AbstractCard card;

    public PlayHandAction(AbstractCard card, AbstractCreature target, boolean exhausts) {
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source =  AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
    }

    public PlayHandAction(AbstractCard card, boolean exhausts) {
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source =  AbstractDungeon.player;
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        this.exhaustCards = exhausts;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if(AbstractDungeon.player.hand.isEmpty()){
                this.isDone = true;
                return;
            }
            if (!AbstractDungeon.player.hand.contains(card)) {
                this.isDone = true;
                return;
            }
            else {
                AbstractDungeon.player.hand.group.remove(card);
                (AbstractDungeon.getCurrRoom()).souls.remove(card);
                card.exhaustOnUseOnce = this.exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;

                card.applyPowers();
                addToTop(new NewQueueCardAction(card, this.target, false, true));
                addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
            this.isDone = true;
        }

    }
}

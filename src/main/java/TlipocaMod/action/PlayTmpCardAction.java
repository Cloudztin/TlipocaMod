package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class PlayTmpCardAction extends AbstractGameAction {
    AbstractCard card;
    public Consumer<AbstractCard> cardConsumer;

    public PlayTmpCardAction(AbstractCard card, Consumer<AbstractCard> cardConsumer) {
        this.card = card;
        this.cardConsumer = cardConsumer;
    }


    public PlayTmpCardAction(AbstractCard card) {
        this(card, (Consumer<AbstractCard>) null);
    }


    public void update() {
        AbstractCard card = this.card.makeStatEquivalentCopy();
        if (this.cardConsumer != null) {
            this.cardConsumer.accept(card);
        }
        card.tags = this.card.tags;
        card.purgeOnUse = true;
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();

        AbstractMonster m = (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        if (m != null) {
            card.calculateCardDamage(m);
        }

        addToTop( new NewQueueCardAction(card,  m, false, true));
        addToTop( new UnlimboAction(card));
        if (!Settings.FAST_MODE) {
            addToTop( new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            addToTop( new WaitAction(Settings.ACTION_DUR_FASTER));
        }
        this.isDone = true;
    }
}

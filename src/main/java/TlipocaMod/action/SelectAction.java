package TlipocaMod.action;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SelectAction extends AbstractGameAction {

    private final Predicate<AbstractCard> predicate;

    private final Consumer<List<AbstractCard>> callback;

    private final String text;

    private final boolean anyNumber;

    private final boolean canPickZero;

    private final ArrayList<AbstractCard> hand;

    private final ArrayList<AbstractCard> tempHand = new ArrayList<>();

    public SelectAction(int amount, String textForSelect, boolean anyNumber, boolean canPickZero, Predicate<AbstractCard> cardFilter, Consumer<List<AbstractCard>> callback) {
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.text = textForSelect;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.predicate = cardFilter;
        this.callback = callback;
        this.hand = AbstractDungeon.player.hand.group;
    }

    public SelectAction(int amount, String textForSelect, Predicate<AbstractCard> cardFilter, Consumer<List<AbstractCard>> callback) {
        this(amount, textForSelect, false, false, cardFilter, callback);
    }

    public SelectAction(int amount, String textForSelect, Consumer<List<AbstractCard>> callback) {
        this(amount, textForSelect, false, false, c -> true, callback);
    }

    public SelectAction(String textForSelect, Predicate<AbstractCard> cardFilter, Consumer<List<AbstractCard>> callback) {
        this(1, textForSelect, false, false, cardFilter, callback);
    }

    public SelectAction(String textForSelect, Consumer<List<AbstractCard>> callback) {
        this(1, textForSelect, false, false, c -> true, callback);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.callback == null) {
                this.isDone = true;
                return;
            }
            this.hand.removeIf(c -> (!this.predicate.test(c) && this.tempHand.add(c)));
            if (this.hand.isEmpty()) {
                finish();
                return;
            }
            if (this.hand.size() <= this.amount && !this.anyNumber && !this.canPickZero) {
                ArrayList<AbstractCard> spoof = new ArrayList<>(this.hand);
                this.hand.clear();
                this.callback.accept(spoof);
                this.hand.addAll(spoof);
                finish();
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(this.text, this.amount, this.anyNumber, this.canPickZero);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            this.callback.accept(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            this.hand.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            finish();
            return;
        }
        tickDuration();
    }

    private void finish() {
        this.hand.addAll(this.tempHand);
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();
        this.isDone = true;
    }
}

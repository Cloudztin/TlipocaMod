package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AutoplayTmpCardAction extends AbstractGameAction {
    private AbstractCard card;
    private AbstractMonster m;

    public AutoplayTmpCardAction(AbstractCard card) {
        this.card = card;
        this.m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }

    public AutoplayTmpCardAction(AbstractCard card, AbstractMonster m) {
        this.card = card;
        this.m = m;
    }

    public void update() {

        AbstractCard tmp = this.card.makeSameInstanceOf();
        tmp.tags = this.card.tags;
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = AbstractDungeon.player.hb.cX+300f*Settings.scale;
        tmp.current_y = AbstractDungeon.player.hb.cY+200f*Settings.scale;
        tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = Settings.HEIGHT / 2.0F;
        tmp.applyPowers();

        if (m != null) {
            tmp.calculateCardDamage(m);
        }

        tmp.purgeOnUse = true;
        addToTop( new NewQueueCardAction(tmp,  m, false, true));
        addToTop( new UnlimboAction(tmp));

        this.isDone = true;
    }


}

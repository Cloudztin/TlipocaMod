package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.cards.tempCards.tlGriefOfTheGreatDeath;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.Iterator;

public class SoulStone extends AbstractTlipocaRelic{

    public static final String relicName = "SoulStone";
    public static final String ID = TlipocaMod.getID(relicName);

    public SoulStone() {
        super(relicName, RelicTier.BOSS, LandingSound.SOLID, true);

        this.counter = 0;
    }


    public void onEquip() {

        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new tlGriefOfTheGreatDeath(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    public void onUnequip() {
        AbstractCard cardToRemove = null;
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c instanceof tlGriefOfTheGreatDeath) {
                cardToRemove = c;
                break;
            }
        }

        if (cardToRemove != null) {
            AbstractDungeon.player.masterDeck.group.remove(cardToRemove);
        }

    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if(!card.purgeOnUse){
            this.counter++;
            if((card.costForTurn==this.counter && !card.freeToPlayOnce) || (card.cost==-1 && card.energyOnUse==this.counter)){
                flash();

                AbstractMonster m = null;
                if (action.target != null)
                    m = (AbstractMonster)action.target;

                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractCard tmp = card.makeSameInstanceOf();
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                tmp.applyPowers();
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            }
        }
    }

    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SoulStone();
    }
}

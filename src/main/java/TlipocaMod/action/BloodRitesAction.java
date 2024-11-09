package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class BloodRitesAction extends AbstractGameAction {
    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("BloodRites").TEXT;
    private AbstractPlayer p;
    private AbstractCard c;
    private AbstractMonster m;
    public boolean cardSelected = true;

    public BloodRitesAction(AbstractPlayer p, AbstractCard c, AbstractMonster m) {
        this.p = p;
        this.c = c;
        this.m = m;
        this.duration = 0.5F;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if(this.m!=null){
                this.m.damage(new DamageInfo(this.p, this.c.damage, this.c.damageTypeForTurn));
                if ((this.m.isDying || this.m.currentHealth <= 0) && !this.m.halfDead && !this.m.hasPower("Minion")){
                    cardSelected = false;

                    CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                    for (AbstractCard card : (AbstractDungeon.player.masterDeck.getPurgeableCards()).group)
                        tmp.addToTop(card);

                    if (tmp.group.isEmpty()){
                        cardSelected = true;
                        this.isDone=true;
                        tickDuration();
                        return;
                    }
                    if(tmp.size()==1){
                        giveCards(tmp.group);
                    }
                    else if(!AbstractDungeon.isScreenUp){
                        AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0] + LocalizedStrings.PERIOD, false, false, false, false);
                        tickDuration();
                        return;
                    }
                    else{
                        AbstractDungeon.dynamicBanner.hide();
                        AbstractDungeon.previousScreen = AbstractDungeon.screen;
                        AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0] + LocalizedStrings.PERIOD, false, false, false, false);
                        tickDuration();
                        return;
                    }




                }
            }


        }

        if(!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1){
            giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
            this.isDone=true;
            tickDuration();
        }
    }

    public void giveCards(ArrayList<AbstractCard> group) {
        cardSelected = true;
        float displayCount = 0.0F;
        for (Iterator<AbstractCard> i = group.iterator(); i.hasNext(); ) {
            AbstractCard card = i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractDungeon.transformCard(card, false, AbstractDungeon.miscRng);

            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM && AbstractDungeon.transformedCard != null) {

                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(

                        AbstractDungeon.getTransformedCard(), Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F, false));



                displayCount += Settings.WIDTH / 6.0F;
            }
        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

}

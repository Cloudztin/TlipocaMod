package TlipocaMod.action;

import TlipocaMod.TlipocaMod.TlipocaModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class StarlightSeizeAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private final boolean upgrade;

    public StarlightSeizeAction(boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgrade = upgrade;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();

            return;
        }

        if(!this.retrieveCard){
            if(AbstractDungeon.cardRewardScreen.discoveryCard !=null){
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                disCard.current_x = -1000.0F * Settings.xScale;

                if (AbstractDungeon.player.hand.size() < 10)
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                else
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;

        }

        tickDuration();
    }
    private ArrayList<AbstractCard> generateCardChoices(){
        ArrayList<AbstractCard> choices = new ArrayList<>();

        while (choices.size()<3){
            AbstractCard.CardRarity cardRarity;
            boolean dupe = false;

            int roll = AbstractDungeon.cardRandomRng.random(99);
            if (roll < 55) cardRarity = AbstractCard.CardRarity.COMMON;
            else{
                if (roll < 85) cardRarity = AbstractCard.CardRarity.UNCOMMON;
                else{
                    cardRarity = AbstractCard.CardRarity.RARE;
                }
            }

            AbstractCard tmp=getXcostCard(cardRarity);


            for (AbstractCard c : choices) if(c.cardID.equals(tmp.cardID)){
                dupe = true;
                break;
            }

            if(!dupe){
                AbstractCard item=tmp.makeCopy();
                if(upgrade) CardModifierManager.addModifier(item, new TlipocaModifier(TlipocaModifier.supportedModify.RESONATE, false));
                choices.add(item);
            }
        }
        return choices;
    }

    private AbstractCard getXcostCard(AbstractCard.CardRarity cardRarity){
        CardGroup pool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var = CardLibrary.cards.entrySet().iterator();

        while(true){
            Map.Entry c ;

            do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    if (!var.hasNext())
                                        if(!pool.isEmpty()){
                                            pool.shuffle(AbstractDungeon.cardRandomRng);
                                            return pool.getRandomCard(true, cardRarity);
                                        } else{
                                            return getXcostCard(AbstractCard.CardRarity.UNCOMMON);
                                        }

                                    c = (Map.Entry)var.next();
                                } while(((AbstractCard)c.getValue()).rarity != cardRarity);
                            } while(((AbstractCard)c.getValue()).hasTag(AbstractCard.CardTags.HEALING));
                        } while(((AbstractCard)c.getValue()).type == AbstractCard.CardType.CURSE);
                    } while(((AbstractCard)c.getValue()).type == AbstractCard.CardType.STATUS);
                } while(((AbstractCard)c.getValue()).cost <3);
            } while(UnlockTracker.isCardLocked((String)c.getKey()) && !Settings.treatEverythingAsUnlocked());

            pool.addToBottom((AbstractCard) c.getValue());
        }


    }

}

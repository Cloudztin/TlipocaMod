package TlipocaMod.action;

import TlipocaMod.cards.tempCards.hllFetter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class SpecialDiscoverAction extends AbstractGameAction {

    private boolean retrieveCard = false;
    private final AbstractCard.CardType cardType;


    public SpecialDiscoverAction(AbstractCard.CardType type) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardType = type;
    }


    public void update() {
        ArrayList<AbstractCard> generatedCards;
        generatedCards = null;
        if (this.cardType == AbstractCard.CardType.CURSE)
            generatedCards = generateCurseCardChoices();

        if (this.cardType == AbstractCard.CardType.STATUS)
            generatedCards = generateStatusCardChoices();


        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], (this.cardType != null));


            tickDuration();

            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if (disCard.type != AbstractCard.CardType.CURSE && disCard.type != AbstractCard.CardType.STATUS) {
                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        disCard.upgrade();
                    }
                    disCard.setCostForTurn(0);
                }
                if (disCard.cardID == "Slimed") {
                    disCard.setCostForTurn(0);
                }


                disCard.current_x = -1000.0F * Settings.xScale;
                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else {

                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }


                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }

        tickDuration();
    }

    private ArrayList<AbstractCard> generateCurseCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Normality());
        list.add(new Injury());
        list.add(new Parasite());
        list.add(new Regret());
        list.add(new Doubt());
        list.add(new Pain());
        list.add(new Clumsy());
        list.add(new Shame());
        list.add(new Decay());
        list.add(new Writhe());


        while (derp.size() != 3) {
            boolean dupe = false;
            AbstractCard tmp = null;
            tmp = list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }

        return derp;
    }

    private ArrayList<AbstractCard> generateStatusCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Slimed());
        list.add(new VoidCard());
        list.add(new Wound());
        list.add(new Burn());
        list.add(new Dazed());
        list.add(new hllFetter());


        while (derp.size() != 3) {
            boolean dupe = false;
            AbstractCard tmp = null;
            tmp = list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }

        return derp;
    }
}

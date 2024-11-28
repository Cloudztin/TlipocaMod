package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CassildasSong extends AbstractTlipocaRelic{

    public static final String relicName = "CassildasSong";
    public static final String ID = TlipocaMod.getID(relicName);

    public CassildasSong() {
        super(relicName, RelicTier.RARE, LandingSound.FLAT, true);
    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if  ((card.costForTurn >=3 && !card.freeToPlayOnce) || (card.cost == -1 && card.energyOnUse >= 3)){
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CassildasSong();
    }
}

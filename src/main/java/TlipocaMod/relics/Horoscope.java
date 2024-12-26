package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Horoscope extends AbstractTlipocaRelic{


    public static final String relicName = "Horoscope";
    public static final String ID = TlipocaMod.getID(relicName);

    public Horoscope() {
        super(relicName, RelicTier.RARE, LandingSound.MAGICAL, true);

    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Horoscope();
    }

}

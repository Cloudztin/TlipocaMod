package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class QuetzalcoatlsFeather extends AbstractTlipocaRelic{


    public static final String relicName = "QuetzalcoatlsFeather";
    public static final String ID = TlipocaMod.getID(relicName);

    public QuetzalcoatlsFeather() {
        super(relicName, RelicTier.BOSS, LandingSound.MAGICAL, true);

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new QuetzalcoatlsFeather();
    }

}

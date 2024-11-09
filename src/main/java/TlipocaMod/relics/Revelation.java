package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.InnateModify;
import TlipocaMod.TlipocaMod.TlipocaMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Revelation extends AbstractTlipocaRelic{

    public static final String relicName = "Revelation";
    public static final String ID = TlipocaMod.getID(relicName);

    public Revelation() {
        super(relicName, RelicTier.BOSS, LandingSound.FLAT,true);
    }


    @Override
    public void onEquip() {
        super.onEquip();
        AbstractCard c= new Apotheosis();
        c.upgrade();
        CardModifierManager.addModifier(c, new InnateModify());
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH * 0.6F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Revelation();
    }

}

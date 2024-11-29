package TlipocaMod.potions;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.cards.choices.tl0;
import TlipocaMod.cards.choices.tl1;
import TlipocaMod.cards.choices.tl2;
import TlipocaMod.cards.choices.tl3;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.ArrayList;

import static TlipocaMod.TlipocaMod.TlipocaMod.Tlipoca_Color;

public class CorrectionFluid extends CustomPotion {

    public static final String potionName = "CorrectionFluid";
    public static final String POTION_ID= TlipocaMod.getID(potionName);
    private static final PotionStrings potionStrings= CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static Color liquidColor= CardHelper.getColor(255, 255, 255);
    public static Color hybridColor=CardHelper.getColor(255, 255, 255);
    public static Color SpotsColor=CardHelper.getColor(255, 255, 255);

    public CorrectionFluid() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.EYE, PotionColor.WHITE);
        this.potency=this.getPotency();
        if(this.potency==1)
            this.description=potionStrings.DESCRIPTIONS[0]+ this.potency+potionStrings.DESCRIPTIONS[1];
        else
            this.description=potionStrings.DESCRIPTIONS[0]+ this.potency+potionStrings.DESCRIPTIONS[2];
        this.isThrown=false;
        this.labOutlineColor=Tlipoca_Color;
    }

    public void initializeData(){
        this.potency=this.getPotency();
        if(this.potency==1)
            this.description=potionStrings.DESCRIPTIONS[0]+ this.potency+potionStrings.DESCRIPTIONS[1];
        else
            this.description=potionStrings.DESCRIPTIONS[0]+ this.potency+potionStrings.DESCRIPTIONS[2];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target){
        for(int i=0; i<this.potency; i++){
            ArrayList<AbstractCard> CostChoices = new ArrayList<>();
            CostChoices.add(new tl0());
            CostChoices.add(new tl1());
            CostChoices.add(new tl2());
            CostChoices.add(new tl3());
            addToBot(new ChooseOneAction(CostChoices));
        }
    }

    public int getPotency(final int potency) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new CorrectionFluid();
    }
}

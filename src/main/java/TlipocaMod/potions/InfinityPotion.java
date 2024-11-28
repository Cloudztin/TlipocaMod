package TlipocaMod.potions;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.powers.InfinityPower;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static TlipocaMod.TlipocaMod.TlipocaMod.Tlipoca_Color;

public class InfinityPotion extends AbstractPotion {

    public static final String potionName = "InfinityPotion";
    public static final String POTION_ID= TlipocaMod.getID(potionName);
    private static final PotionStrings potionStrings= CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static Color liquidColor= null;
    public static Color hybridColor=null;
    public static Color SpotsColor=null;


    public InfinityPotion() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.RARE, PotionSize.EYE, PotionEffect.RAINBOW, Color.WHITE, null, null);
        this.potency=this.getPotency();
        if(this.potency==1)
            this.description=potionStrings.DESCRIPTIONS[0];
        if(this.potency>1)
            this.description=potionStrings.DESCRIPTIONS[1];
        this.isThrown=false;
        this.labOutlineColor=Tlipoca_Color;
    }

    public void initializeData(){
        this.potency=this.getPotency();
        if(this.potency==1)
            this.description=potionStrings.DESCRIPTIONS[0];
        if(this.potency>1)
            this.description=potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target){
        addToBot(new ExpertiseAction(AbstractDungeon.player, 8));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new InfinityPower(AbstractDungeon.player, this.potency), this.potency));
    }

    public int getPotency(final int potency) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new InfinityPotion();
    }
}

package TlipocaMod.potions;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.powers.BleedingPower;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;


import static TlipocaMod.TlipocaMod.TlipocaMod.Tlipoca_Color;

public class BottledDisease extends CustomPotion {

    public static final String potionName = "BottledDisease";
    public static final String POTION_ID= TlipocaMod.getID(potionName);
    private static final PotionStrings potionStrings= CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static Color liquidColor=Color.PINK.cpy();
    public static Color hybridColor=Color.BLACK.cpy();
    public static Color SpotsColor=null;


    public BottledDisease() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.JAR, PotionColor.FEAR);
        this.potency=this.getPotency();
        this.description=potionStrings.DESCRIPTIONS[0]+ this.potency+potionStrings.DESCRIPTIONS[1];
        this.isThrown=true;
        this.targetRequired=true;
        this.labOutlineColor=Tlipoca_Color;
    }

    public void initializeData(){
        this.potency=this.getPotency();
        this.description=potionStrings.DESCRIPTIONS[0]+ this.potency+potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target){
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, this.potency), this.potency));
    }

    public int getPotency(final int potency) {
        return 2;
    }

    public AbstractPotion makeCopy() {
        return new BottledDisease();
    }
}

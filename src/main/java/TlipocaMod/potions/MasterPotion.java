package TlipocaMod.potions;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.powers.MasteryPower;
import TlipocaMod.powers.SheathedPower;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static TlipocaMod.TlipocaMod.TlipocaMod.HaaLouLing_Color;

public class MasterPotion extends AbstractPotion {

    public static final String potionName = "MasterPotion";
    public static final String POTION_ID = TlipocaMod.getID(potionName);
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String[] TEXT =CardCrawlGame.languagePack.getUIString("PotionString").TEXT;

    public static Color liquidColor = HaaLouLing_Color.cpy();
    public static Color hybridColor = null;
    public static Color SpotsColor = null;


    public MasterPotion() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionEffect.NONE, liquidColor, hybridColor, SpotsColor);
        this.potency = this.getPotency();
        if (this.potency == 1)
            this.description = potionStrings.DESCRIPTIONS[0];
        if (this.potency > 1)
            this.description = potionStrings.DESCRIPTIONS[1];
        this.isThrown = false;
        this.labOutlineColor = HaaLouLing_Color;
    }

    public void initializeData() {
        this.potency = this.getPotency();
        if (this.potency == 1)
            this.description = potionStrings.DESCRIPTIONS[0];
        if (this.potency > 1)
            this.description = potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper(TEXT[1])),  GameDictionary.keywords.get(TEXT[1])));
    }

    public void use(AbstractCreature target) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SheathedPower(AbstractDungeon.player)));
        addToBot(new DiscardPileToTopOfDeckAction(AbstractDungeon.player));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MasteryPower(AbstractDungeon.player, this.potency)));
    }

    public int getPotency(final int potency) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new MasterPotion();
    }
}

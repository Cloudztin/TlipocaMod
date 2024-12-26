package TlipocaMod.potions;

import TlipocaMod.TlipocaMod.TlipocaMod;
import TlipocaMod.patches.CardPatch;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TlipocaMod.TlipocaMod.TlipocaMod.HaaLouLing_Color;

public class SpringWater extends AbstractPotion {

    public static final String potionName = "SpringWater";
    public static final String POTION_ID= TlipocaMod.getID(potionName);
    private static final PotionStrings potionStrings= CardCrawlGame.languagePack.getPotionString(POTION_ID);



    public SpringWater() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.RARE, PotionSize.HEART, PotionEffect.NONE, Color.CLEAR.cpy(), Color.CLEAR.cpy(), Color.CLEAR.cpy());


        this.potency = this.getPotency();
        if (this.potency == 5)
            this.description = potionStrings.DESCRIPTIONS[0];
        else
            this.description = potionStrings.DESCRIPTIONS[1];
        this.isThrown=false;
        this.labOutlineColor=HaaLouLing_Color;
    }

    public void initializeData(){
        this.potency = this.getPotency();
        if (this.potency == 5)
            this.description = potionStrings.DESCRIPTIONS[0];
        else
            this.description = potionStrings.DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new
                PowerTip(TipHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]),
                GameDictionary.keywords.get(GameDictionary.EXHAUST.NAMES[0])));
    }

    public void use(AbstractCreature target){
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
            addToBot(new RemoveDebuffsAction(AbstractDungeon.player));
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.potency));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    int amt=0;
                    for(AbstractCard c:AbstractDungeon.player.hand.group)
                        if(c.cost==-2 || CardPatch.newVarField.lockNUM.get(c)>0){
                            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                            amt++;
                        }
                    addToBot(new DrawCardAction(amt));

                    this.isDone=true;
                }
            });
        }
        else
            AbstractDungeon.player.heal(5);

    }

    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return false;

        return (AbstractDungeon.getCurrRoom()).event == null ||
                !((AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain);
    }

    public int getPotency(final int potency) {
        return 5;
    }

    public AbstractPotion makeCopy() {
        return new SpringWater();
    }
}
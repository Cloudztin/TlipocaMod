package TlipocaMod.patches;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import TlipocaMod.powers.AbstractTlipocaPower;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RandomizeHandCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConfusionPower;

import java.util.ArrayList;


public class PowerPatch {

    @SpirePatch(clz= AbstractMonster.class, method = "die", paramtypez={boolean.class})
    public static class DeathPatch1{
        @SpireInsertPatch(rloc = 9)
        public static void Insert(AbstractMonster mo) {
            for(AbstractPower p: AbstractDungeon.player.powers)
                if(p instanceof AbstractTlipocaPower)
                    ((AbstractTlipocaPower)p).onMonsterDeath(mo);
        }
    }

    @SpirePatch(clz= ConfusionPower.class, method="onCardDraw")
    public static class ConfusionPatch{
        @SpireInsertPatch(rloc=5, localvars = {"newCost"})
        public static void Insert(AbstractPower power, AbstractCard card, int newCost) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
            if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                if(((CostForTurnModifier) mods.get(0)).trueCost >=0)
                    ((CostForTurnModifier) mods.get(0)).trueCost = newCost;
        }
    }

    @SpirePatch(clz= RandomizeHandCostAction.class, method="update")
    public static class RandomizeHandCostActionPatch{
        @SpireInsertPatch(rloc=8, localvars = {"card","newCost"})
        public static void Insert(RandomizeHandCostAction action, AbstractCard card, int newCost) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
            if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                if(((CostForTurnModifier) mods.get(0)).trueCost >=0)
                    ((CostForTurnModifier) mods.get(0)).trueCost = newCost;
        }
    }
}

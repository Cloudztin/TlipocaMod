package TlipocaMod.patches;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import TlipocaMod.action.IncreaseCostForTurnAction;
import TlipocaMod.action.LowerCostForTurnAction;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.EnlightenmentAction;
import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CardPatch {
    // define the keywords: EPHEMERAL, ETERNITY, RESONATE, TWINCAST


    @SpirePatch(clz = AbstractCard.class, method = "<class>")
    public static class newVarField {
        public static SpireField<Boolean> resonate = new SpireField<>(() -> false);
        public static SpireField<Boolean> ephemeral = new SpireField<>(() -> false);
        public static SpireField<Boolean> eternity = new SpireField<>(() -> false);
        public static SpireField<Integer> eternelCost =new SpireField<>(()->-2);
        public static SpireField<Boolean> updated =new SpireField<>(()->false);
        public static SpireField<Boolean> twinCast =new SpireField<>(()->false);
        public static SpireField<Boolean> rebound=new SpireField<>(()->false);
    }

    @SpirePatch(clz=AbstractCard.class,method = "update")
    public static class costUpdatePatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard card) {
            if(card.cost >=0){
                if (CardPatch.newVarField.eternity.get(card)) {
                    if (!newVarField.updated.get(card)) {
                        newVarField.eternelCost.set(card, card.costForTurn);
                        newVarField.updated.set(card, true);
                    }
                    card.costForTurn = newVarField.eternelCost.get(card);
                    card.cost = newVarField.eternelCost.get(card);
                    card.freeToPlayOnce = false;
                    card.isCostModified = false;
                    card.isCostModifiedForTurn = false;
                } else {
                    newVarField.updated.set(card, false);
                }
            }
        }

    }

    @SpirePatch(clz= AbstractCard.class,method = "triggerOnOtherCardPlayed")
    static public class triggerOnOtherCardPlayedPatch {
        @SpirePrefixPatch
        static public void Prefix(AbstractCard card,AbstractCard c) {
            boolean addCost = false;
            boolean reduceCost = false;
            if(newVarField.ephemeral.get(card) && card.cost>=0)
                addCost = true;
            if(newVarField.resonate.get(card) && card.cost>=0 &&  ( (c.costForTurn>=0 && (c.costForTurn<=card.costForTurn || c.freeToPlayOnce)) || (c.cost==-1 && c.energyOnUse<=card.costForTurn) ))
                if(card.costForTurn>0 || card.cost>0)
                    reduceCost = true;
            if(addCost)
                AbstractDungeon.actionManager.addToBottom(new IncreaseCostForTurnAction(card, 1));
            if(reduceCost)
                AbstractDungeon.actionManager.addToBottom(new LowerCostForTurnAction(card, 1));
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    static public class makeStatEquivalentCopyPatch {
        @SpireInsertPatch(rloc = 22, localvars = {"card"})
        static public void Insert(AbstractCard c, AbstractCard card) {
            newVarField.eternity.set(card, newVarField.eternity.get(c));
            newVarField.updated.set(card, newVarField.updated.get(c));
            newVarField.ephemeral.set(card, newVarField.ephemeral.get(c));
            newVarField.eternelCost.set(card, newVarField.eternelCost.get(c));
            newVarField.resonate.set(card, newVarField.resonate.get(c));
        }
    }

    @SpirePatch(clz= AbstractCard.class, method ="upgradeBaseCost")
    static public class upgradeBaseCostPatch {
        @SpirePrefixPatch
        static public void Prefix(AbstractCard card, int newBaseCost) {
            if(card.cost>=0){
                ArrayList<AbstractCardModifier> mods =  CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
                if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                    if(((CostForTurnModifier) mods.get(0)).trueCost>=0 && newBaseCost>=0)
                        ((CostForTurnModifier) mods.get(0)).trueCost=newBaseCost;
            }
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="updateCost")
    static public class updateCostPatch {
        @SpirePrefixPatch
        static public void Prefix(AbstractCard card, int amt) {
            if (card.color == AbstractCard.CardColor.CURSE && !card.cardID.equals("Pride") || card.type == AbstractCard.CardType.STATUS && !card.cardID.equals("Slimed")) {
            } else {
                if (card.cost >= 0) {
                    ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
                    if (!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                        if(((CostForTurnModifier) mods.get(0)).trueCost>=0){

                            ((CostForTurnModifier) mods.get(0)).trueCost += amt;
                            if (((CostForTurnModifier) mods.get(0)).trueCost < 0)
                                ((CostForTurnModifier) mods.get(0)).trueCost = 0;

                    }
                }
            }
        }
    }

    @SpirePatch(clz= AbstractCard.class, method = "modifyCostForCombat")
    static public class modifyCostForCombatPatch {
        @SpirePrefixPatch
        static public void Prefix(AbstractCard card, int amt) {
            if(card.cost >= 0) {
                ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, CostForTurnModifier.ID);
                if (!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                    if(((CostForTurnModifier) mods.get(0)).trueCost>=0){
                        ((CostForTurnModifier) mods.get(0)).trueCost += amt;
                        if(((CostForTurnModifier) mods.get(0)).trueCost < 0)
                            ((CostForTurnModifier) mods.get(0)).trueCost = 0;
                }
            }
        }
    }

    @SpirePatch(clz= EnlightenmentAction.class, method = "update")
    static public class updateEnlightenmentPatch {
        @SpirePrefixPatch
        static public void Prefix(EnlightenmentAction action, float ___duration) {
            if (___duration == Settings.ACTION_DUR_FAST){
                for(AbstractCard c: AbstractDungeon.player.hand.group){
                    if( c.cost>=0 ){
                        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, CostForTurnModifier.ID);
                        if (!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                            if(((CostForTurnModifier) mods.get(0)).trueCost>1)
                                ((CostForTurnModifier) mods.get(0)).trueCost=1;
                    }
                }
            }
        }
    }

    @SpirePatch( clz= MadnessAction.class, method="findAndModifyCard")
    static public class updateMadnessPatch {
        @SpireInsertPatch(rlocs = {4, 15}, localvars = {"c"})
        static public void Insert(MadnessAction action, AbstractCard c) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, CostForTurnModifier.ID);
            if (!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                if(((CostForTurnModifier) mods.get(0)).trueCost>0)
                    ((CostForTurnModifier) mods.get(0)).trueCost=0;
        }
    }

    @SpirePatch( clz= MadnessAction.class, method="update")
    static public class updateMadness2Patch {
        @SpireInsertPatch(rloc = 10, localvars = {"betterPossible","possible"})
        static public void Insert(MadnessAction action, boolean betterPossible, boolean possible) {

            if(!betterPossible && !possible){
                CardGroup newGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for(AbstractCard c: AbstractDungeon.player.hand.group)
                    if( c.cost==0 ){
                        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, CostForTurnModifier.ID);
                        if(!mods.isEmpty() && mods.get(0) instanceof CostForTurnModifier)
                            if(((CostForTurnModifier) mods.get(0)).trueCost>0)
                                newGroup.addToTop(c);
                    }

                if(!newGroup.isEmpty()){
                    AbstractCard c=newGroup.getRandomCard(AbstractDungeon.cardRandomRng);

                    c.cost=0;
                    c.costForTurn=0;
                    ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, CostForTurnModifier.ID);
                    ((CostForTurnModifier) mods.get(0)).trueCost=0;
                    c.superFlash(Color.GOLD.cpy());
                }

            }
        }
    }

    @SpirePatch(clz = UseCardAction.class, method = "update")
    static public class useCardActionPatch {
        @SpireInsertPatch(rloc = 7)
        static public void Insert(UseCardAction action ,AbstractCard ___targetCard) {
            if(newVarField.twinCast.get(___targetCard) && !___targetCard.purgeOnUse){
                AbstractMonster m = null;

                if (action.target != null) {
                    m = (AbstractMonster)action.target;
                }

                AbstractCard tmp = ___targetCard.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = ___targetCard.current_x;
                tmp.current_y = ___targetCard.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;

                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, ___targetCard.energyOnUse, true, true), true);
            }
        }

        @SpirePrefixPatch
        static public void Prefix(UseCardAction action ,AbstractCard ___targetCard) {
            if(newVarField.rebound.get(___targetCard)){
                action.reboundCard=true;
                newVarField.rebound.set(___targetCard,false);
            }
        }
    }


    public static class cardCostUnit{
        public int index;
        public int eternelCost;
        public boolean eternity;
        public boolean updated;
    }



}

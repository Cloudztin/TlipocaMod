package TlipocaMod.patches;

import TlipocaMod.action.IncreaseCostForTurnAction;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
                if(card.costForTurn>0)
                    reduceCost = true;
            if(addCost)
                AbstractDungeon.actionManager.addToBottom(new IncreaseCostForTurnAction(card, 1));
            if(reduceCost)
                AbstractDungeon.actionManager.addToBottom(new ReduceCostForTurnAction(card, 1));
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

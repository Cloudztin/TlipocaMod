package TlipocaMod.patches;

import TlipocaMod.TlipocaMod.CostForTurnModifier;
import TlipocaMod.action.IncreaseCostForTurnAction;
import TlipocaMod.action.LowerCostForTurnAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.powers.AbstractTlipocaPower;
import TlipocaMod.relics.AbstractTlipocaRelic;
import TlipocaMod.relics.Horoscope;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.unique.EnlightenmentAction;
import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

import java.util.ArrayList;

public class CardPatch {
    public static final String[] TEXT;
    public static final Texture smallTimer = ImageMaster.loadImage("TlipocaModResources/img/basic/LockedCard.png");
    public static final Texture timer = ImageMaster.loadImage("TlipocaModResources/img/basic/CountDown.png");
    public static final TextureAtlas.AtlasRegion anima_atlas = new TextureAtlas.AtlasRegion(smallTimer, 0, 0, 512, 512);
    public static final TextureAtlas.AtlasRegion CountDown = new TextureAtlas.AtlasRegion(timer, 0, 0, 91, 91);


    static {
        TEXT = CardCrawlGame.languagePack.getUIString("LockPatch").TEXT;


    }
    

    public static void intoLock(AbstractCard card, int n) {
        if (n <= 0) return;
        if (!newVarField.canLock.get(card)) return;
        int lockNum = newVarField.lockNUM.get(card);
        if (lockNum > 0) return;
        lockNum = Math.min(n, 9999);
        newVarField.lockNUM.set(card, lockNum);
        card.flash();
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractHaaLouLingCard)
                ((AbstractHaaLouLingCard) c).onLock(card);
        }
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof AbstractTlipocaRelic)
                ((AbstractTlipocaRelic) r).onLock(card);
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractTlipocaPower) {
                ((AbstractTlipocaPower) p).onLock(card);
                ((AbstractTlipocaPower) p).onNumUp(card, n);
            }
        }
        if (card instanceof AbstractHaaLouLingCard) {
            ((AbstractHaaLouLingCard) card).intoLock(n);
        }
    }

    public static void lockNumUp(AbstractCard card, int n) {
        if (n <= 0) return;
        if (!newVarField.canLock.get(card)) return;
        int lockNum = newVarField.lockNUM.get(card);
        if (lockNum > 0) {
            lockNum += n;
            if (lockNum > 9999)
                lockNum = 9999;
            newVarField.lockNUM.set(card, lockNum);
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractTlipocaPower)
                    ((AbstractTlipocaPower) p).onNumUp(card, n);
            }
        } else {
            intoLock(card, n);
        }
    }

    public static void zeroLock(AbstractCard card) {
        int lockNum = newVarField.lockNUM.get(card);
        if (lockNum == 0) return;
        newVarField.lockNUM.set(card, 0);
        card.flash();
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractHaaLouLingCard)
                ((AbstractHaaLouLingCard) c).onUnlock(card);
        }
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof AbstractTlipocaRelic)
                ((AbstractTlipocaRelic) r).onUnlock(card);
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractTlipocaPower) {
                ((AbstractTlipocaPower) p).onUnlock(card);
                ((AbstractTlipocaPower) p).onNumDes(card, lockNum);
            }
        }
        if (card instanceof AbstractHaaLouLingCard) {
            ((AbstractHaaLouLingCard) card).zeroLock(lockNum);
        }
    }

    public static void lockNumDes(AbstractCard card, int n) {
        if (n <= 0) return;
        int lockNum = newVarField.lockNUM.get(card);
        if (lockNum == 0) return;
        if (lockNum > n) {
            lockNum -= n;
            newVarField.lockNUM.set(card, lockNum);
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractTlipocaPower)
                    ((AbstractTlipocaPower) p).onNumDes(card, n);
            }
        } else {
            zeroLock(card);
        }
    }

    public static void renderHelper(AbstractCard c, final SpriteBatch sb, final float x, final float y, final TextureAtlas.AtlasRegion img) {
        if (img != null) {
            sb.setColor(Color.WHITE);
            sb.draw(img, x + img.offsetX - img.originalWidth / 2.0f, y + img.offsetY - img.originalHeight / 2.0f, img.originalWidth / 2.0f - img.offsetX, img.originalHeight / 2.0f - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, c.drawScale * Settings.scale, c.drawScale * Settings.scale, c.angle);
        }
    }


    public static void renderLock(final SpriteBatch sb, AbstractCard c) {
        if (!c.keywords.contains(TEXT[2])) c.keywords.add(TEXT[2]);
        Color costColor = Color.SKY.cpy();
        CardPatch.renderHelper(c, sb, c.current_x, c.current_y, anima_atlas);
        costColor.a = c.transparency;
        String text = Integer.toString(newVarField.lockNUM.get(c));
        if (!newVarField.canLock.get(c)) {
            text = "X";
            costColor = Color.PURPLE.cpy();
        }
        FontHelper.cardEnergyFont_L.getData().setScale(c.drawScale);
        final BitmapFont font = FontHelper.cardEnergyFont_L;
        FontHelper.renderRotatedText(sb, font, text, c.current_x, c.current_y, -132.0f * c.drawScale * Settings.scale, 120.0f * c.drawScale * Settings.scale, c.angle, false, costColor);
    }


    @SpirePatch(clz = AbstractCard.class, method = "canUse")
    public static class CanUsePatch {
        @SpirePostfixPatch
        static public boolean Postfix(boolean _r, AbstractCard card, AbstractPlayer p, AbstractMonster m) {
            if (!_r) return false;
            if(p.hasRelic(Horoscope.ID))
                return true;
            boolean inHand = AbstractDungeon.player.hand.group.contains(card);
            if (inHand && newVarField.lockNUM.get(card) > 0) {
                card.cantUseMessage = TEXT[1];
                return false;
                }
            return true;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "onRetained")
    static public class onRetainedPatch {
        @SpirePostfixPatch
        static public void Postfix(AbstractCard card) {
            if (!AbstractDungeon.player.hasRelic(Horoscope.ID))
                if (newVarField.lockNUM.get(card) > 0)
                    CardPatch.lockNumDes(card, 1);
        }
    }


    @SpirePatch(clz = DiscardAtEndOfTurnAction.class, method = "update")
    static public class updatePatch {
        @SpireInsertPatch(rloc = 5, localvars = {"e"})
        static public void Insert(DiscardAtEndOfTurnAction action, AbstractCard e) {
            if (newVarField.lockNUM.get(e) > 0) {
                e.retain = true;
            }
        }
    }


    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class renderOrbPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard card, SpriteBatch sb) {
            if (newVarField.lockNUM.get(card) > 0)
                CardPatch.renderLock(sb, card);
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderCost")
    public static class renderPatch {
        @SpirePostfixPatch
        public static void Postfix(final SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard ___card) {
            if (newVarField.lockNUM.get(___card) > 0) {
                if (!___card.keywords.contains(TEXT[2])) ___card.keywords.add(TEXT[2]);
                sb.draw(CountDown, ((Settings.WIDTH / 2.0f) - (300.0f * Settings.scale) + CountDown.offsetX) - (CountDown.originalWidth / 2.0f), ((Settings.HEIGHT / 2.0f) + (300.0f * Settings.scale) + CountDown.offsetY) - (CountDown.originalHeight / 2.0f), (CountDown.originalWidth / 2.0f) - CountDown.offsetX, (CountDown.originalHeight / 2.0f) - CountDown.offsetY, 0.7f * CountDown.packedWidth, 0.7f * CountDown.packedHeight, Settings.scale, Settings.scale, 0.0f);
                Color costColor = Color.SKY.cpy();
                costColor.a = ___card.transparency;
                String text = Integer.toString(newVarField.lockNUM.get(___card));
                FontHelper.cardEnergyFont_L.getData().setScale(___card.drawScale);
                FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, text, Settings.WIDTH / 2.0f - 290.0f * Settings.scale, Settings.HEIGHT / 2.0f + 285.0f * Settings.scale, costColor);


            }
        }
    }
    // define the keywords: EPHEMERAL, ETERNITY, RESONATE, TWINCAST, LOCK, MASTERY, UNLOCKABLE
    
    @SpirePatch(clz = AbstractCard.class, method = "<class>")
    public static class newVarField {
        public static SpireField<Boolean> resonate = new SpireField<>(() -> false);
        public static SpireField<Boolean> ephemeral = new SpireField<>(() -> false);
        public static SpireField<Boolean> eternity = new SpireField<>(() -> false);
        public static SpireField<Integer> eternalCost =new SpireField<>(()->-2);
        public static SpireField<Boolean> updated =new SpireField<>(()->false);
        public static SpireField<Boolean> twinCast =new SpireField<>(()->false);
        public static SpireField<Boolean> rebound=new SpireField<>(()->false);
        public static SpireField<Integer> lockNUM = new SpireField<Integer>(() -> 0);
        public static SpireField<Boolean> canLock = new SpireField<Boolean>(() -> true);
        public static SpireField<Boolean> mastery = new SpireField<>(() -> false);

    }

    @SpirePatch(clz=AbstractCard.class,method = "update")
    public static class costUpdatePatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard card) {
            if(card.cost >=0){
                if (CardPatch.newVarField.eternity.get(card)) {
                    if (!newVarField.updated.get(card)) {
                        if(card.freeToPlayOnce)
                            newVarField.eternalCost.set(card, 0);
                        else
                            newVarField.eternalCost.set(card, card.costForTurn);
                        newVarField.updated.set(card, true);
                    }
                    else
                        card.costForTurn = newVarField.eternalCost.get(card);
                    card.cost = newVarField.eternalCost.get(card);
                    card.freeToPlayOnce = false;
                    card.isCostModified = false;
                    card.isCostModifiedForTurn = false;
                } else {
                    newVarField.updated.set(card, false);
                }
            }
        }

    }

    @SpirePatch(clz=AbstractCard.class,method = "freeToPlay")
    public static class freeToPlayPatch {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractCard card) {
            if(card.cost >=0 && CardPatch.newVarField.eternity.get(card))
                return SpireReturn.Return(false);
            else
                return SpireReturn.Continue();
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
            newVarField.eternalCost.set(card, newVarField.eternalCost.get(c));
            newVarField.resonate.set(card, newVarField.resonate.get(c));
            newVarField.twinCast.set(card, newVarField.twinCast.get(c));
            newVarField.rebound.set(card, newVarField.rebound.get(c));
            newVarField.canLock.set(card, newVarField.canLock.get(c));
            newVarField.mastery.set(card, newVarField.mastery.get(c));
            newVarField.lockNUM.set(card, newVarField.lockNUM.get(c));
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

    @SpirePatch(clz= AbstractCard.class, method = "triggerOnGlowCheck")
    static public class triggerOnGlowCheckPatch {
        @SpirePrefixPatch
        static public void Prefix(AbstractCard card, ArrayList<CardGlowBorder> ___glowList) {
            ___glowList.clear();
            if(newVarField.eternity.get(card)){
                card.glowColor=Color.NAVY.cpy();
                return;
            }
            if(newVarField.ephemeral.get(card) && newVarField.resonate.get(card))
                card.glowColor=Color.LIGHT_GRAY.cpy();
            if(newVarField.ephemeral.get(card) &&(!newVarField.resonate.get(card)))
                card.glowColor=Color.PINK.cpy();
            if((!newVarField.ephemeral.get(card)) && newVarField.resonate.get(card))
                card.glowColor=Color.LIME.cpy();
            if((!newVarField.ephemeral.get(card)) && (!newVarField.resonate.get(card)))
                card.glowColor=new Color(0.2F, 0.9F, 1.0F, 0.25F);
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
        public int eternalCost;
        public boolean eternity;
        public boolean updated;
    }



}

package TlipocaMod.action;

import TlipocaMod.cards.common.hllBrokenBlade;
import TlipocaMod.cards.common.hllRustyHilt;
import TlipocaMod.cards.common.hllAncientScabbard;
import TlipocaMod.cards.special.hllIllusionBlade;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class GetBladeAction extends AbstractGameAction {

    public void update() {
        boolean BladePlayed=false;
        boolean HiltPlayed=false;
        boolean ScabbardPlayed=false;
        boolean BladeGot=false;
        boolean HiltGot=false;
        boolean ScabbardGot=false;
        boolean SwordGot=false;

        for(AbstractCard c:AbstractDungeon.actionManager.cardsPlayedThisCombat){
            if(c.cardID.equals(hllBrokenBlade.ID)) BladePlayed=true;
            if(c.cardID.equals(hllRustyHilt.ID)) HiltPlayed=true;
            if(c.cardID.equals(hllAncientScabbard.ID)) ScabbardPlayed=true;
        }

        for(AbstractCard c:AbstractDungeon.player.masterDeck.group){
            if(c.cardID.equals(hllBrokenBlade.ID)) BladeGot=true;
            if(c.cardID.equals(hllRustyHilt.ID)) HiltGot=true;
            if(c.cardID.equals(hllAncientScabbard.ID)) ScabbardGot=true;
            if(c.cardID.equals(hllIllusionBlade.ID)) SwordGot=true;
        }

        if(BladePlayed && HiltPlayed && ScabbardPlayed && BladeGot && HiltGot && ScabbardGot && !SwordGot){
            for(int i=AbstractDungeon.player.masterDeck.group.size()-1; i>=0; i-- ){
                AbstractCard c=AbstractDungeon.player.masterDeck.group.get(i);
                if((c.cardID.equals(hllBrokenBlade.ID) ||
                        c.cardID.equals(hllAncientScabbard.ID) ||
                        c.cardID.equals(hllRustyHilt.ID) )&&
                !c.inBottleLightning &&! c.inBottleTornado && !c.inBottleFlame){
                    AbstractDungeon.effectList.add(new PurgeCardEffect(c));
                    AbstractDungeon.player.masterDeck.group.remove(c);
                }
            }

            AbstractCard c= new hllIllusionBlade();
            c.rarity= AbstractCard.CardRarity.RARE;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

            AbstractCard c2= new hllIllusionBlade();
            c2.rarity= AbstractCard.CardRarity.RARE;
            c2.setCostForTurn(0);
            addToTop(new MakeTempCardInHandAction(c2));
        }

        this.isDone=true;
    }
}

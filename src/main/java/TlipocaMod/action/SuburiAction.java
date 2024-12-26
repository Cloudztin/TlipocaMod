package TlipocaMod.action;

import TlipocaMod.patches.CardPatch;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

public class SuburiAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("UnlockHandAction").TEXT;
    private final List<AbstractCard> cannotChange=new ArrayList<>();
    private final AbstractPlayer p= AbstractDungeon.player;
    private final AbstractMonster m;
    private final DamageInfo info;

    public SuburiAction(AbstractMonster m, DamageInfo info) {
        this.info = info;
        this.m = m;
        this.duration= Settings.ACTION_DUR_MED;
    }


    public void update() {
        if(duration==Settings.ACTION_DUR_MED) {
            int avai=0;
            for(AbstractCard c:p.hand.group)
                if(CardPatch.newVarField.lockNUM.get(c)>0)
                    avai++;

            if(avai==0){
                this.isDone=true;
                return;
            }

            if(avai==1){
                for(AbstractCard c:p.hand.group)
                    if(CardPatch.newVarField.lockNUM.get(c)>0)
                        CardPatch.zeroLock(c);
                addToTop(new DamageAction(m, info, AttackEffect.SLASH_HEAVY));
            }

            if(avai>=2){
                for(AbstractCard c:p.hand.group)
                    if(CardPatch.newVarField.lockNUM.get(c)==0)
                        cannotChange.add(c);

                p.hand.group.removeAll(cannotChange);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1,false, false, false, false);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }


        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard c=AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
            CardPatch.zeroLock(c);
            c.applyPowers();
            this.p.hand.addToTop(c);
            c.superFlash(Color.WHITE.cpy());
            addToTop(new DamageAction(m, info, AttackEffect.SLASH_HEAVY));

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
    }

    private void returnCards(){
        for(AbstractCard c:this.cannotChange)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}

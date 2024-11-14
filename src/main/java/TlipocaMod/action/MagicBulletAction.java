package TlipocaMod.action;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;


public class MagicBulletAction extends AbstractGameAction {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("MagicBulletAction").TEXT;

    private final DamageInfo info;
    private final AbstractPlayer p= AbstractDungeon.player;

    public MagicBulletAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if(this.duration==Settings.ACTION_DUR_MED) {
            if(this.p.drawPile.isEmpty()) {
                this.isDone=true;
                return;
            }

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (int i = 0; i < Math.min(3, this.p.drawPile.size()); i++)
                tmpGroup.addToTop(this.p.drawPile.group.get(this.p.drawPile.size() - i - 1));

            AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, false, TEXT[0]);
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                int energy=0;
                if(c.cost>=0) energy=c.costForTurn;
                if(c.cost==-1) energy= EnergyPanel.getCurrentEnergy();
                for(int i=0; i<energy; i++) addToTop(new DamageAction(this.target, info, AttackEffect.BLUNT_HEAVY));
                if(this.p.hand.size()== BaseMod.MAX_HAND_SIZE)
                    this.p.createHandIsFullDialog();
                else
                    this.p.drawPile.moveToHand(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone=true;
        }
        tickDuration();
    }

}

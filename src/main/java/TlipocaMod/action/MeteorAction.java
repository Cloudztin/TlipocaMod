package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MeteorAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int amount;
    private final AbstractCard card;

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("MeteorAction").TEXT;

    public MeteorAction( int amount, AbstractCard card) {
        this.p= AbstractDungeon.player;
        this.card = card;
        this.amount = amount;
        this.duration=0.5f;
    }

    public void update() {
        if(this.duration==0.5f) {
            if(this.p.drawPile.isEmpty()) {
                this.isDone=true;
                return;
            }

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (int i = 0; i < Math.min(this.amount, this.p.drawPile.size()); i++)
                tmpGroup.addToTop(this.p.drawPile.group.get(this.p.drawPile.size() - i - 1));

            AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, false, TEXT[0]);
        }
        else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                int energy=0;
                if(c.cost>=0) energy=c.costForTurn;
                if(c.cost==-1) energy= EnergyPanel.getCurrentEnergy();
                card.baseDamage= energy*energy*2;
                card.calculateCardDamage(AbstractDungeon.getMonsters().monsters.get(0));
                addToTop(new DamageAllEnemiesAction(this.p, card.multiDamage, card.damageTypeForTurn, AttackEffect.BLUNT_HEAVY));
                addToTop(new ExhaustSpecificCardAction(c, this.p.drawPile, true));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }


}

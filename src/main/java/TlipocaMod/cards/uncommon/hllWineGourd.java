package TlipocaMod.cards.uncommon;

import TlipocaMod.action.SpecialDiscoverAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllWineGourd extends AbstractHaaLouLingCard {


    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "WineGourd";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllWineGourd() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.NONE);

        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = getLastCardPlayedThisCombat();
        if (c != null) {
            if(c.type==CardType.SKILL || c.type == CardType.ATTACK || c.type == CardType.POWER)
                addToBot(new DiscoveryAction(c.type, 1));
            else addToBot(new SpecialDiscoverAction(c.type));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new hllWineGourd();
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;
        if (getLastCardPlayedThisCombat() == null) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        super.update();
        if (this.hb.hovered)
            if (getLastCardPlayedThisCombat() != null)
                this.cardsToPreview = getLastCardPlayedThisCombat().makeCopy();
    }


    public AbstractCard getLastCardPlayedThisCombat() {
        for (int i = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1; i >= 0; i--) {
            AbstractCard c = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(i);
            if (!c.purgeOnUse && !c.equals(this)) return c;
        }
        return null;
    }
}

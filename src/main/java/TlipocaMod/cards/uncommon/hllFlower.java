package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.cards.tempCards.hllPetal;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllFlower extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Flower";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllFlower() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.NONE);

        this.magicNumber = this.baseMagicNumber = 2;
        this.cardsToPreview = new hllPetal();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c=new hllPetal();
        if(this.upgraded) c.upgrade();
        addToBot(new MakeTempCardInDiscardAction(c.makeStatEquivalentCopy(), this.magicNumber));
    }

    @Override
    public void onRetained() {
        super.onRetained();
        AbstractCard c=new hllPetal();
        if(this.upgraded) c.upgrade();
        addToBot(new MakeTempCardInDiscardAction(c.makeStatEquivalentCopy(), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.cardsToPreview.upgrade();
            this.initializeDescription();
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new hllFlower();
    }
}

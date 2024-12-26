package TlipocaMod.cards.uncommon;

import TlipocaMod.action.DisintegrationAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.cards.tempCards.hllFetter;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllDisintegration extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Disintegration";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllDisintegration() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.cardsToPreview=new hllFetter();
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DisintegrationAction());
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
        return new hllDisintegration();
    }
}

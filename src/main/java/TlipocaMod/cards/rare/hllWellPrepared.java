package TlipocaMod.cards.rare;

import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.powers.WellPreparedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllWellPrepared extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.POWER;
    static final int cost = 0;
    static final String cardName = "WellPrepared";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllWellPrepared() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.magicNumber=this.baseMagicNumber=1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WellPreparedPower(p, magicNumber)));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.isInnate=true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllWellPrepared();
    }
}

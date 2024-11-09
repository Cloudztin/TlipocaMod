package TlipocaMod.cards.uncommon;

import TlipocaMod.action.MeteorAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlMeteor extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "Meteor";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlMeteor() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);


        this.magicNumber=this.baseMagicNumber=3;
        this.baseDamage=0;
        this.isMultiDamage=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MeteorAction(this.magicNumber, this));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new tlMeteor();
    }
}

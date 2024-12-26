package TlipocaMod.cards.uncommon;


import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlSolidify extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 0;
    static final String cardName = "Solidify";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlSolidify() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);


        this.magicNumber=this.baseMagicNumber=4;
        this.baseBlock=this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!this.freeToPlayOnce)
            this.baseBlock=this.magicNumber* (int) Math.pow(2, this.costForTurn);
        else this.baseBlock=this.magicNumber;
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void update() {
        super.update();
        if(!this.freeToPlayOnce)
            this.baseBlock=this.magicNumber* (int) Math.pow(2, this.costForTurn);
        else this.baseBlock=this.magicNumber;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(2);
            if(!this.freeToPlayOnce)
                this.baseBlock=this.magicNumber* (int) Math.pow(2, this.costForTurn);
            else this.baseBlock=this.magicNumber;
        }
    }


    public AbstractCard makeCopy() {
        return new tlSolidify();
    }
}

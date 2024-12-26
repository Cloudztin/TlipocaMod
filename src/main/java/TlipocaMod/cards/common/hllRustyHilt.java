package TlipocaMod.cards.common;

import TlipocaMod.action.GetBladeAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllRustyHilt extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "RustyHilt";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllRustyHilt() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);


        this.baseBlock=3;
        this.magicNumber=this.baseMagicNumber=3;
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i=0; i<this.magicNumber; i++)
            addToBot(new GainBlockAction(p, this.block));
        addToBot(new GetBladeAction());
    }


    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllRustyHilt();
    }
}

package TlipocaMod.cards.common;

import TlipocaMod.action.RustyBladeAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;


public class tlRustyBlade extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "RustyBlade";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlRustyBlade() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=7;
        this.magicNumber=this.baseMagicNumber=2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new RustyBladeAction(p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlRustyBlade();
    }
}

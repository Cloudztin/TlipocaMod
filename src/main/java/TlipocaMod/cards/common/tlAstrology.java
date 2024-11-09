package TlipocaMod.cards.common;

import TlipocaMod.action.AstrologyAction;
import TlipocaMod.action.IncreaseHandCostAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlAstrology extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "Astrology";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlAstrology() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=8;
        this.magicNumber=this.baseMagicNumber=3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new AstrologyAction(p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlAstrology();
    }
}

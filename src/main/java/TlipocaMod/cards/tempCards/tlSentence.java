package TlipocaMod.cards.tempCards;

import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlSentence extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.SPECIAL;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "Sentence";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlSentence() {
        super(CardColor.COLORLESS ,ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.magicNumber=this.baseMagicNumber = 0;
        this.baseDamage=7;
    }

    public void setX(int amount) {
        this.magicNumber = amount;

        this.baseMagicNumber = this.magicNumber;
        this.rawDescription = this.baseMagicNumber == 1 ? cardStrings.EXTENDED_DESCRIPTION[1] : cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new ExpungeVFXAction(m));
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }

    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeDamage(5);
        }
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        card.baseMagicNumber = this.baseMagicNumber;
        card.magicNumber = this.magicNumber;
        card.description = (ArrayList)this.description.clone();
        return card;
    }

    public AbstractCard makeCopy() {
        return new tlSentence();
    }
}

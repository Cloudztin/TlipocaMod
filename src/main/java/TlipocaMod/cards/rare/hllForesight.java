package TlipocaMod.cards.rare;

import TlipocaMod.action.DetectAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;

import TlipocaMod.powers.SheathedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllForesight extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.SKILL;
    static final int cost = 2;
    static final String cardName = "Foresight";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllForesight() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DetectAction(m, this.magicNumber));
        addToBot(new DiscardPileToTopOfDeckAction(p));
        addToBot(new ApplyPowerAction(p, p, new SheathedPower(p)));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllForesight();
    }
}
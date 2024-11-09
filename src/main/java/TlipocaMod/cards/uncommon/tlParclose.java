package TlipocaMod.cards.uncommon;

import TlipocaMod.action.ParcloseAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlParclose extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Parclose";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlParclose() {
        super(ID, cardStrings.NAME ,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);


        this.baseBlock=3;
        this.magicNumber=this.baseMagicNumber=2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ParcloseAction(p, p, this));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlParclose();
    }
}

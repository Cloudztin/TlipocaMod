package TlipocaMod.cards.common;

import TlipocaMod.action.DrawHightestCostAction;
import TlipocaMod.action.Randomize1HandCostAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;


public class tlCleanse extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Cleanse";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlCleanse() {
        super(ID, cardStrings.NAME ,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);


        this.baseBlock=9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new Randomize1HandCostAction(p));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlCleanse();
    }
}

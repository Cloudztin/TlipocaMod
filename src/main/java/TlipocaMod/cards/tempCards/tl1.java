package TlipocaMod.cards.tempCards;

import TlipocaMod.action.ChangeCostAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tl1 extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.STATUS;
    static final int cost = -2;
    static final String cardName = "1";


    public static final String ID = getID(cardName);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path = "TlipocaModResources/img/cards/Tlipoca/1.png";

    public tl1() {
        super(CardColor.COLORLESS, ID, cardStrings.NAME, img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.NONE);
    }


    @Override
    public void upgrade() {
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    public void onChoseThisOption() {
        addToTop(new ChangeCostAction(AbstractDungeon.player, 1));
    }

    public AbstractCard makeCopy() {
        return new tl1();
    }

}

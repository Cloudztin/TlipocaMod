package TlipocaMod.cards.uncommon;

import TlipocaMod.action.SelectAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllInnerForce extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 2;
    static final String cardName = "InnerForce";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllInnerForce() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectAction(1, cardStrings.EXTENDED_DESCRIPTION[0], false, false, card ->
                CardPatch.newVarField.canLock.get(card), abstractCards -> {
            AbstractCard c=abstractCards.get(0);
            CardPatch.lockNumUp(c, CardPatch.newVarField.lockNUM.get(c));
            addToBot(new GainBlockAction(p, CardPatch.newVarField.lockNUM.get(c)));
        }
        ));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllInnerForce();
    }
}

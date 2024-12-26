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

public class hllUnleash extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Unleash";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllUnleash() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.baseBlock=4;
        this.magicNumber=this.baseMagicNumber=4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectAction(1, cardStrings.EXTENDED_DESCRIPTION[0], false, false, card ->
                CardPatch.newVarField.lockNUM.get(card) > 0,
                abstractCards -> {
                    int count = 0;
                    AbstractCard c=abstractCards.get(0);
                        count = Math.min(CardPatch.newVarField.lockNUM.get(c), this.magicNumber);
                        CardPatch.lockNumDes(c, this.magicNumber);
                    for (int i = 0; i < count; i++)
                        addToBot(new GainBlockAction(p, this.block));
                }
        ));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(1);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new hllUnleash();
    }
}

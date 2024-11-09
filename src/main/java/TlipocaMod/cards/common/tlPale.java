package TlipocaMod.cards.common;


import TlipocaMod.action.ReduceHandCostAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlPale extends AbstractTlipocaCard {
    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Pale";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlPale() {
        super(ID, cardStrings.NAME ,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);


        this.baseBlock=6;
        this.magicNumber=this.baseMagicNumber=1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new ReduceHandCostAction(1, false, this.magicNumber));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlPale();
    }
}

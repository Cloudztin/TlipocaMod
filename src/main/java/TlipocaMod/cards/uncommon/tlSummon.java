package TlipocaMod.cards.uncommon;

import TlipocaMod.action.SummonAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlSummon extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Summon";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlSummon() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SummonAction());
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.exhaust=false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new tlSummon();
    }
}

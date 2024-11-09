package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.powers.CollapsePower;
import TlipocaMod.powers.UnleashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlCollapse extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.POWER;
    static final int cost = 2;
    static final String cardName = "Collapse";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlCollapse() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.isEthereal=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new CollapsePower(p )));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.isEthereal=false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new tlCollapse();
    }
}

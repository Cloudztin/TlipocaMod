package TlipocaMod.cards.rare;

import TlipocaMod.action.CoronateAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.powers.GrievousWoundsPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlCoronate extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.POWER;
    static final int cost = -1;
    static final String cardName = "Coronate";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlCoronate() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CoronateAction(p ,this.magicNumber, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return new tlCoronate();
    }
}

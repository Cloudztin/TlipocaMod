package TlipocaMod.cards.rare;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.powers.BleedingPower;
import TlipocaMod.powers.SacrificePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlSacrifice extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Sacrifice";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlSacrifice() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!m.hasPower(SacrificePower.ID))
            addToBot(new ApplyPowerAction(m, p, new SacrificePower(m)));
        if(upgraded)
            addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new tlSacrifice()
                ;
    }
}

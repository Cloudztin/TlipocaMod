package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlMystery extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 0;
    static final String cardName = "Mystery";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlMystery(){
        this(0);
    }

    public tlMystery(int upgrades) {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        CardPatch.newVarField.ephemeral.set(this, true);
        this.magicNumber=this.baseMagicNumber=1;
        this.exhaust=true;
        this.timesUpgraded = upgrades;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    public boolean canUpgrade() {
        /* 58 */     return true;
        /*    */   }

    @Override
    public void upgrade() {
        this.timesUpgraded++;
        this.upgraded = true;
        upgradeMagicNumber(1);

        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();

        if(this.timesUpgraded<17){
            StringBuilder energyBar= new StringBuilder();
            for(int i=0; i<this.timesUpgraded; i++) energyBar.append(cardStrings.EXTENDED_DESCRIPTION[1]);

            this.rawDescription=cardStrings.EXTENDED_DESCRIPTION[0];
            this.rawDescription+=energyBar.toString();
            this.rawDescription+=cardStrings.EXTENDED_DESCRIPTION[2];
        }
        else this.rawDescription =cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }


    public AbstractCard makeCopy() {
        return new tlMystery();
    }
}

package TlipocaMod.cards.basic;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlDeterrent extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.BASIC;
    static final CardType type = CardType.SKILL;
    static final int cost = 0;
    static final String cardName = "Deterrent";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlDeterrent() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.magicNumber=this.baseMagicNumber=1;
        CardPatch.newVarField.ephemeral.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
        addToBot(new WaitAction(0.5f));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new tlDeterrent();
    }
}

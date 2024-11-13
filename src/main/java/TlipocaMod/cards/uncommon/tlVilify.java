package TlipocaMod.cards.uncommon;

import TlipocaMod.action.SummonAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlVilify extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Vilify";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlVilify() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt=0;
        if(m!=null)
            for(AbstractPower po: m.powers)
                if(po.type == AbstractPower.PowerType.DEBUFF) amt++;
        if(amt>0)
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amt)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            CardPatch.newVarField.resonate.set(this, true);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new tlVilify();
    }
}

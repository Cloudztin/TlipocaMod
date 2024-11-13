package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlInsight extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "Insight";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlInsight() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.magicNumber=this.baseMagicNumber=2;
        this.baseDamage=8;
        this.baseBlock=8;
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m!=null){
            addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, this.magicNumber), this.magicNumber));
            if(m.getIntentBaseDmg()>=0)
                addToBot(new GainBlockAction(p, this.baseBlock));
            else
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(1);
            upgradeBlock(3);
            upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy() {
        return new tlInsight();
    }
}

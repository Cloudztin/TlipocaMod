package TlipocaMod.cards.common;

import TlipocaMod.action.IncreaseHandCostAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlDust extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "Dust";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlDust() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=2;
        this.baseBlock=2;
        this.magicNumber=this.baseMagicNumber=1;
        CardPatch.newVarField.eternity.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlDust();
    }
}

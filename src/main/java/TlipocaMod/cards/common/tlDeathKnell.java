package TlipocaMod.cards.common;

import TlipocaMod.action.AstrologyAction;
import TlipocaMod.action.ModifyCostAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlDeathKnell extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "DeathKnell";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlDeathKnell() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        this.selfRetain=true;
        this.baseDamage=8;
        this.shuffleBackIntoDrawPile=true;
        this.magicNumber=this.baseMagicNumber=1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new ModifyDamageAction(this.uuid, this.baseDamage));
        addToBot(new ModifyCostAction(this.uuid, this.magicNumber));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlDeathKnell();
    }
}

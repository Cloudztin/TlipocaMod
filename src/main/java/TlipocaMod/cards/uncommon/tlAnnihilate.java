package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlAnnihilate extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 2;
    static final String cardName = "Annihilate";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlAnnihilate() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);


        this.magicNumber=this.baseMagicNumber=3;
        this.baseDamage=14;
        this.isMultiDamage=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -1)));
    }


    public void applyPowers() {
             AbstractPower strength = AbstractDungeon.player.getPower("Strength");
             if (strength != null) {
                   strength.amount *= this.magicNumber;
                 }

             super.applyPowers();

             if (strength != null) {
                   strength.amount /= this.magicNumber;
                 }
           }


       public void calculateCardDamage(AbstractMonster mo) {
             AbstractPower strength = AbstractDungeon.player.getPower("Strength");
             if (strength != null) {
                   strength.amount *= this.magicNumber;
                 }

             super.calculateCardDamage(mo);

             if (strength != null) {
                   strength.amount /= this.magicNumber;
                 }
           }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeDamage(4);
        }
    }

    public AbstractCard makeCopy() {
        return new tlAnnihilate();
    }
}

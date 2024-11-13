package TlipocaMod.cards.basic;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlSoulStrike extends AbstractTlipocaCard {
        static final CardRarity rarity = CardRarity.BASIC;
        static final CardType type = CardType.ATTACK;
        static final int cost = 2;
        static final String cardName = "SoulStrike";


        public static final String ID=getID(cardName);
        private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
        private static final String img_path=loadTlipocaCardImg(cardName,type);

        public tlSoulStrike() {
            super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


            this.baseDamage=4;
            this.magicNumber=this.baseMagicNumber=2;

            CardPatch.newVarField.resonate.set(this, true);
        }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            for (int i=0; i<this.magicNumber; i++) addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
            return new tlSoulStrike();
    }
}

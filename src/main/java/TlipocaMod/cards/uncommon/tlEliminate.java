package TlipocaMod.cards.uncommon;

import TlipocaMod.action.EliminateAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;


import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlEliminate extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 3;
    static final String cardName = "Eliminate";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlEliminate() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=26;
        this.tags.add(CardTags.HEALING);
        this.magicNumber=this.baseMagicNumber=3;
        this.misc=0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m!=null)
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        addToBot(new WaitAction(0.8f));
        if(this.misc>0)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        else
            addToBot(new EliminateAction(p, m, this));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeDamage(8);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if(this.misc==0)
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c=super.makeStatEquivalentCopy();
        c.misc=0;
        return c;
    }

    @Override
    public AbstractCard makeSameInstanceOf() {
        AbstractCard c=super.makeSameInstanceOf();
        c.misc=this.misc;
        return c;
    }

    public AbstractCard makeCopy() {
        return new tlEliminate();
    }
}

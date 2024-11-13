package TlipocaMod.cards.rare;


import TlipocaMod.action.JusticeAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlJustitia extends AbstractTlipocaCard {

    public static final String[] TEXT =  CardCrawlGame.languagePack.getUIString("Justitia").TEXT;
    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.ATTACK;
    static final int cost = 4;
    static final String cardName = "Justitia";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlJustitia() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        this.baseDamage=40;
        this.magicNumber=this.baseMagicNumber=1;
        this.misc=0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m!=null){
            AbstractDungeon.effectList.add(new WeightyImpactEffect(m.hb.cX, m.hb.cY));
            addToBot(new JusticeAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber, this.uuid));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeDamage(10);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(this.costForTurn==0 || this.freeToPlay())
            return super.canUse(p, m);
        else{
            this.cantUseMessage=TEXT[0];
            return false;
        }


    }

    @Override
    public void triggerOnGlowCheck() {
        if(this.costForTurn==0 || this.freeToPlay())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.UPGRADE_DESCRIPTION;
        this.initializeTitle();
    }

    public AbstractCard makeCopy() {
        return new tlJustitia();
    }
}

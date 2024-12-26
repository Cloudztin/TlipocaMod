package TlipocaMod.cards.tempCards;

import TlipocaMod.action.PetalEffect;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllPetal extends AbstractHaaLouLingCard {


    static final CardRarity rarity = CardRarity.SPECIAL;
    static final CardType type = CardType.ATTACK;
    static final int cost = 0;
    static final String cardName = "Petal";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllPetal() {
        super(CardColor.COLORLESS ,ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        this.baseDamage=4;
        CardPatch.newVarField.mastery.set(this, true);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new PetalEffect(m.hb.cX, m.hb.cY)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        this.exhaust = true;
        for(AbstractCard c:p.hand.group)
            if (!c.equals(this) && c instanceof hllPetal) {
                this.exhaust = false;
                break;
            }
        if(this.exhaust)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for(AbstractCard c: p.discardPile.group)
                        if(c instanceof hllPetal)
                            addToTop(new DiscardToHandAction(c));

                    this.isDone=true;
                }
            });
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeDamage(2);
        }
    }


    public AbstractCard makeCopy() {
        return new hllPetal();
    }
}

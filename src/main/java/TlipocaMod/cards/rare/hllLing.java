package TlipocaMod.cards.rare;

import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllLing extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.ATTACK;
    static final int cost = 0;
    static final String cardName = "Ling";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllLing() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);


        this.baseDamage=12;
        this.isMultiDamage=true;
        CardPatch.newVarField.canLock.set(this, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean over=false;
        for(AbstractCard c: p.hand.group)
            if(!c.equals(this) && c.canUse(p, m)){
                over=true;
                break;
            }

        if(!over)
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }


    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        boolean over=false;
        for(AbstractCard c: AbstractDungeon.player.hand.group)
            if(!c.equals(this) && c.canUse(AbstractDungeon.player, null)){
                over=true;
                break;
            }

        if(!over)
            glowColor=GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllLing();
    }
}

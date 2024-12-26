package TlipocaMod.cards.common;

import TlipocaMod.cards.AbstractHaaLouLingCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllQuickDraw extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "QuickDraw";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllQuickDraw() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=8;
        this.magicNumber=this.baseMagicNumber=2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        boolean tri = true;
        for (int i = 0; i < AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1; i++)
            if (AbstractDungeon.actionManager.cardsPlayedThisTurn.get(i).type == CardType.ATTACK) {
                tri = false;
                break;
            }
        if(tri){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber)));
            addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        boolean trigger = true;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn)
            if (c.type == CardType.ATTACK) {
                trigger = false;
                break;
            }

        if (trigger)
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }


    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllQuickDraw();
    }
}

package TlipocaMod.cards.rare;

import TlipocaMod.action.RemoveBuffsAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllCleaveSouls extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.ATTACK;
    static final int cost = 2;
    static final String cardName = "CleaveSouls";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllCleaveSouls() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);


        this.baseDamage=19;
        this.isMagicNumberModified=true;
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean attack = false;
        boolean power = false;
        boolean skill = false;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) attack = true;
            if (c.type == CardType.SKILL) skill = true;
            if (c.type == CardType.POWER) power = true;
        }
        addToBot( new SFXAction("ATTACK_HEAVY"));
        addToBot( new VFXAction( p,  new CleaveEffect(), 0.1F));
        addToBot( new DamageAllEnemiesAction( p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        if (attack && power && skill) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
                addToBot(new RemoveBuffsAction(mo));
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();

        boolean power = false;
        boolean skill = false;

        if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty())
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                if (c.type == CardType.SKILL) skill = true;
                if (c.type == CardType.POWER) power = true;
            }


        if (power && skill)
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllCleaveSouls();
    }
}

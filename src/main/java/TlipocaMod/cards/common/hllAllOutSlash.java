package TlipocaMod.cards.common;

import TlipocaMod.action.SelectAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllAllOutSlash extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "AllOutSlash";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);
    private static final String[] TEXT=CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public hllAllOutSlash() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=0;
        this.magicNumber=this.baseMagicNumber=3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int max = p.hand.group.stream().mapToInt(c -> CardPatch.newVarField.lockNUM.get(c)).filter(c -> c >= 0).max().orElse(0);
        this.baseDamage = this.magicNumber * max;
        calculateCardDamage(m);
        if (max > 0) {
            addToBot(new SelectAction(1, TEXT[0], false, false, card ->
                    CardPatch.newVarField.lockNUM.get(card) == max, abstractCards ->
            {
                for (AbstractCard c : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(c, p.hand));
                }
            }));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers() {
        int max = AbstractDungeon.player.hand.group.stream().mapToInt(c -> CardPatch.newVarField.lockNUM.get(c)).filter(c -> c >= 0).max().orElse(0);
        this.baseDamage = this.magicNumber * max;
        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllAllOutSlash();
    }
}

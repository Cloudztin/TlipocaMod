package TlipocaMod.cards.rare;


import TlipocaMod.action.RendAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.powers.BleedingPower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlRend extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.ATTACK;
    static final int cost = 1;
    static final String cardName = "Rend";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlRend() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=9;
        this.magicNumber=this.baseMagicNumber=2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RendAction(p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowersToBlock();
        AbstractPlayer player = AbstractDungeon.player;
        this.isDamageModified = false;
        float tmp = (float) this.baseDamage;
        Iterator var9 = player.relics.iterator();

        while (var9.hasNext()) {
            AbstractRelic r = (AbstractRelic) var9.next();
            tmp = r.atDamageModify(tmp, this);
            if (this.baseDamage != (int) tmp) {
                this.isDamageModified = true;
            }
        }

        AbstractPower p;
        for (var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var9.next();
        }

        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseDamage != (int) tmp) {
            this.isDamageModified = true;
        }

        for (var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var9.next();
        }

        if(mo.hasPower(BleedingPower.ID))
            tmp*=2;

        for (var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var9.next();
        }

        for (var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var9.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        if (this.baseDamage != MathUtils.floor(tmp)) {
            this.isDamageModified = true;
        }

        this.damage = MathUtils.floor(tmp);
    }


    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlRend();
    }
}

package TlipocaMod.cards.rare;

import TlipocaMod.action.ExecutionAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;

import java.util.Iterator;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlExecution extends AbstractTlipocaCard {
    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.ATTACK;
    static final int cost = 0;
    static final String cardName = "Execution";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlExecution() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        CardPatch.newVarField.ephemeral.set(this, true);
        this.baseDamage=6;
        this.magicNumber=this.baseMagicNumber=3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m!=null){
            addToBot(new VFXAction(new GoldenSlashEffect(m.hb.cX - 60.0F * Settings.scale, m.hb.cY, false), 0.1f));
            addToBot(new ExecutionAction(this, m));
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        int maxCost=0;
        for(AbstractCard c:AbstractDungeon.player.hand.group)
            if(c.costForTurn>maxCost)
                maxCost=c.costForTurn;
        if(this.costForTurn>=maxCost)
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy(){
        return new tlExecution();
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

        for (var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var9.next();
        }

        for (var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var9.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        int maxCost=0;
        for(AbstractCard c:AbstractDungeon.player.hand.group)
            if(c.costForTurn>maxCost)
                maxCost=c.costForTurn;
        if(this.costForTurn>=maxCost)
            
            tmp*=this.baseMagicNumber;

        if (this.baseDamage != MathUtils.floor(tmp)) {
            this.isDamageModified = true;
        }

        this.damage = MathUtils.floor(tmp);
    }
}

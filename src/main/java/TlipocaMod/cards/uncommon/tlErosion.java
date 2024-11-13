package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlErosion extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 2;
    static final String cardName = "Erosion";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlErosion() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);


        this.magicNumber=this.baseMagicNumber=5;
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        if (Settings.FAST_MODE)
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.PURPLE_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        else
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.PURPLE_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
            addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber)));
            if(!mo.hasPower(ArtifactPower.POWER_ID))
                addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber)));
            addToBot(new ApplyPowerAction(mo, p, new BleedingPower(mo, this.magicNumber)));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy() {
        return new tlErosion();
    }
}

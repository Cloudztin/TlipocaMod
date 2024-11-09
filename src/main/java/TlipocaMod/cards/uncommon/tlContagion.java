package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.powers.BleedingPower;
import TlipocaMod.powers.BrokenTimePower;
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
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.relics.defect.VirusUnlock;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlContagion extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Contagion";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlContagion() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);


        this.magicNumber=this.baseMagicNumber=3;
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE)
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.PURPLE_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        else
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.PURPLE_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) if(mo.hasPower(BleedingPower.ID)) {
            addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false)));
            addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false)));
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
        return new tlContagion();
    }
}

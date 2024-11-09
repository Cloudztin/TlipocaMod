package TlipocaMod.cards.rare;


import TlipocaMod.action.KillPlayerAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.cards.basic.tlDeterrent;
import TlipocaMod.patches.CardPatch;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlJustitia extends AbstractTlipocaCard {



    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Justitia";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlJustitia() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL);

        this.magicNumber = this.baseMagicNumber = 18;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.WHITE, true));
        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
        if(p.hasPower(StrengthPower.POWER_ID))
            if(p.getPower(StrengthPower.POWER_ID).amount >=this.magicNumber){
                addToBot(new VFXAction(new LightningEffect(p.hb.cX, p.hb.cY)));
                addToBot(new KillPlayerAction());
            }

        for(AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters)
            if(mo.hasPower(StrengthPower.POWER_ID))
                if(mo.getPower(StrengthPower.POWER_ID).amount >=this.magicNumber){
                    addToBot(new VFXAction(new LightningEffect(mo.hb.cX, mo.hb.cY)));
                    addToBot(new InstantKillAction(mo));
                }

    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeBaseCost(0);
        }
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

package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import TlipocaMod.powers.BrokenTimePower;
import TlipocaMod.powers.SamsaraPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlSamsara extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 1;
    static final String cardName = "Samsara";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlSamsara() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.magicNumber = this.baseMagicNumber = 1;

        this.exhaust=true;
        CardPatch.newVarField.eternity.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SkipEnemiesTurnAction());
        addToBot(new PressEndTurnButtonAction());
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
        addToBot(new ApplyPowerAction(p, p, new SamsaraPower(p) ));
    }


    public void applyPowers() {
             AbstractPower strength = AbstractDungeon.player.getPower("Strength");
             if (strength != null) {
                   strength.amount *= this.magicNumber;
                 }

             super.applyPowers();

             if (strength != null) {
                   strength.amount /= this.magicNumber;
                 }
           }


       public void calculateCardDamage(AbstractMonster mo) {
             AbstractPower strength = AbstractDungeon.player.getPower("Strength");
             if (strength != null) {
                   strength.amount *= this.magicNumber;
                 }

             super.calculateCardDamage(mo);
             if (strength != null) {
                   strength.amount /= this.magicNumber;
                 }
           }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }


    public AbstractCard makeCopy() {
        return new tlSamsara();
    }
}

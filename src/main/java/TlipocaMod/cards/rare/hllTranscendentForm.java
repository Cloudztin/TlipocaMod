package TlipocaMod.cards.rare;

import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.powers.TranscendentPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllTranscendentForm extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.POWER;
    static final int cost = 5;
    static final String cardName = "TranscendentForm";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllTranscendentForm() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.cardsToPreview=new Miracle();
        this.isEthereal=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("STANCE_ENTER_DIVINITY"));
        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Divinity"));
        addToTop(new VFXAction(new SanctityEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        addToTop(new SFXAction("HEAL_1"));
        addToBot(new ApplyPowerAction(p, p, new TranscendentPower(p)));
        addToBot(new RemoveDebuffsAction(p));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(4);
            this.isEthereal=false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void onRetained() {
        super.onRetained();
        addToBot(new MakeTempCardInHandAction(new Miracle(), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllTranscendentForm();
    }
}

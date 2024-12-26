package TlipocaMod.cards.uncommon;

import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import TlipocaMod.powers.SheathedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllHaaLouCrossDouble extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 2;
    static final String cardName = "HaaLouCrossDouble";
    private boolean isCopy=false;

    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllHaaLouCrossDouble() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        this.baseDamage=6;
        this.magicNumber=this.baseMagicNumber=1;
        CardPatch.newVarField.mastery.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(SheathedPower.ID) && !this.isCopy){
            AbstractCard card = this.makeSameInstanceOf();
            final hllHaaLouCrossDouble tmpCr =(hllHaaLouCrossDouble) card;
            tmpCr.isCopy = true;
            AbstractDungeon.player.limbo.addToBottom(tmpCr);
            tmpCr.current_x = this.current_x;
            tmpCr.current_y = this.current_y;
            tmpCr.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            tmpCr.target_y = Settings.HEIGHT / 2.0f;
            tmpCr.freeToPlayOnce = true;
            tmpCr.purgeOnUse = true;

            if (m != null)
                tmpCr.calculateCardDamage(m);

            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmpCr, m, this.energyOnUse));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false)));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllHaaLouCrossDouble();
    }
}

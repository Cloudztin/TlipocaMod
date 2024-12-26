package TlipocaMod.cards.tempCards;

import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MedicalKit;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllFetter extends AbstractHaaLouLingCard {


    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.STATUS;
    static final int cost = -2;
    static final String cardName = "Fetter";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path="TlipocaModResources/img/cards/HaaLouLing/Fetter.png";

    public hllFetter() {
        super(CardColor.COLORLESS ,ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.NONE);

        CardPatch.newVarField.lockNUM.set(this, 6);
        this.isEthereal=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        boolean Med = false;
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof MedicalKit) {
                Med = true;
                break;
            }
        }
        cantUseMessage = cardStrings.UPGRADE_DESCRIPTION;
        return Med;
    }


    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    public AbstractCard makeCopy() {
        return new hllFetter();
    }
}

package TlipocaMod.cards.uncommon;

import TlipocaMod.action.LeechAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.cards.basic.tlDeterrent;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlLeech extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = -1;
    static final String cardName = "Leech";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlLeech() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LeechAction(p, m, this.upgraded, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new tlLeech();
    }
}

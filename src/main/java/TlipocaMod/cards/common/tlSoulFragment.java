package TlipocaMod.cards.common;

import TlipocaMod.action.IncreaseHandCostForCombatAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlSoulFragment extends AbstractTlipocaCard {
    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = 0;
    static final String cardName = "SoulFragment";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlSoulFragment() {
        super(ID, cardStrings.NAME ,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.magicNumber=this.baseMagicNumber=3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseHandCostForCombatAction(1, !this.upgraded, 1));
        addToBot(new GainEnergyAction(this.magicNumber));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlSoulFragment();
    }
}

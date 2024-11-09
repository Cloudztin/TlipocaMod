package TlipocaMod.cards.uncommon;

import TlipocaMod.action.LeechAction;
import TlipocaMod.action.MaintenanceAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlMaintenance extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.SKILL;
    static final int cost = -1;
    static final String cardName = "Maintenance";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlMaintenance() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF );

        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MaintenanceAction(p, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
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
        return new tlMaintenance();
    }
}

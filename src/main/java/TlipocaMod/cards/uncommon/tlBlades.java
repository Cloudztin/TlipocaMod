package TlipocaMod.cards.uncommon;

import TlipocaMod.action.BladesAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlBlades extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = -1;
    static final String cardName = "Blades";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlBlades() {
        super(ID, cardStrings.NAME ,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);

        this.baseDamage=0;

        this.isMultiDamage=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = EnergyPanel.totalCount;
        if(this.upgraded) this.baseDamage++;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            this.baseDamage+=2;
            p.getRelic("Chemical X").flash();
        }
        calculateCardDamage(m);
        addToBot(new BladesAction(p, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
        if(this.upgraded) this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
        else this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers() {
        this.baseDamage = EnergyPanel.totalCount;
        if(this.upgraded) this.baseDamage++;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)) this.baseDamage+=2;

        super.applyPowers();

        if(this.upgraded) this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
        else this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        if(this.upgraded) this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
        else this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(this.upgraded) this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;
        else this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
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
        return new tlBlades();
    }
}

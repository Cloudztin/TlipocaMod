package TlipocaMod.cards.rare;

import TlipocaMod.action.DeckToTopAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlDeathEye extends AbstractTlipocaCard {


    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.SKILL;
    static final int cost = 0;
    static final String cardName = "DeathEye";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlDeathEye() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.SELF);

        this.magicNumber = this.baseMagicNumber = 4;
        CardPatch.newVarField.eternity.set(this, true);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED, true));
        for(int i=0; i<this.magicNumber; i++)
            addToBot(new DeckToTopAction());
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.isInnate=true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new tlDeathEye();
    }
}

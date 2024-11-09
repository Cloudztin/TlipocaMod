package TlipocaMod.cards.common;

import TlipocaMod.action.GainTopCardEnergyAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.patches.CardPatch;
import com.badlogic.gdx.Audio;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlGaze extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.COMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 2;
    static final String cardName = "Gaze";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlGaze() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=14;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new DamageAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new GainTopCardEnergyAction(p));
        this.cardsToPreview=null;
    }


    public void applyPowers(){
        if(!AbstractDungeon.player.drawPile.isEmpty())
            this.cardsToPreview= AbstractDungeon.player.drawPile.getTopCard().makeSameInstanceOf();
        else this.cardsToPreview=null;
        super.applyPowers();
    }

    public void onMoveToDiscard(){
        this.cardsToPreview=null;
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new tlGaze();
    }
}

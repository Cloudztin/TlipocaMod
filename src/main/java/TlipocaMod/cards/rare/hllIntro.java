package TlipocaMod.cards.rare;

import TlipocaMod.action.CalamityAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;


public class hllIntro extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.ATTACK;
    static final int cost = 0;
    static final String cardName = "Intro";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllIntro() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);


        this.baseDamage=9;
        this.magicNumber=this.baseMagicNumber=1;
        CardPatch.newVarField.mastery.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size()==1){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for(AbstractCard c: AbstractDungeon.player.hand.group)
                        if(CardPatch.newVarField.lockNUM.get(c)>0)
                            CardPatch.zeroLock(c);
                    this.isDone=true;
                }
            });

            addToBot(new GainEnergyAction(this.magicNumber));
            addToBot(new DrawCardAction(2));
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty())
            glowColor=GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.isInnate=true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllIntro();
    }
}

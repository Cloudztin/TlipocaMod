package TlipocaMod.cards.rare;

import TlipocaMod.action.CalamityAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.patches.CardPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllCalamity extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.ATTACK;
    static final int cost = 3;
    static final String cardName = "Calamity";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public hllCalamity() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ALL_ENEMY);


        this.baseDamage=6;
        this.magicNumber=this.baseMagicNumber=6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CalamityAction(this));
    }


    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        boolean over=false;
        List<Integer> costList = new ArrayList<>();

        for(AbstractCard c: AbstractDungeon.player.hand.group)
            if(!c.equals(this) && CardPatch.newVarField.canLock.get(c) && CardPatch.newVarField.lockNUM.get(c)==0 && c.cost!=-2){
                if(costList.contains(c.costForTurn)){
                    over=true;
                    break;
                }
                else
                    costList.add(c.costForTurn);
            }

        if(!over)
            glowColor=GOLD_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllCalamity();
    }
}

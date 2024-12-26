package TlipocaMod.cards.uncommon;

import TlipocaMod.action.HeartPierceAction;
import TlipocaMod.cards.AbstractTlipocaCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlHeartPierceStrike extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.UNCOMMON;
    static final CardType type = CardType.ATTACK;
    static final int cost = 2;
    static final String cardName = "HeartPierceStrike";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadTlipocaCardImg(cardName,type);

    public tlHeartPierceStrike() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.ENEMY);

        this.tags.add(CardTags.STRIKE);
        this.baseDamage=6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HeartPierceAction(p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    public AbstractCard makeCopy() {
        return new tlHeartPierceStrike();
    }
}

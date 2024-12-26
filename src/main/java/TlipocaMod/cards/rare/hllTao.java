package TlipocaMod.cards.rare;

import TlipocaMod.action.PlaySameInstanceAction;
import TlipocaMod.action.TaoAction;
import TlipocaMod.cards.AbstractHaaLouLingCard;
import TlipocaMod.cards.tempCards.hllFetter;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static TlipocaMod.TlipocaMod.TlipocaMod.getHLLID;

public class hllTao extends AbstractHaaLouLingCard {

    static final CardRarity rarity = CardRarity.RARE;
    static final CardType type = CardType.SKILL;
    static final int cost = 4;
    static final String cardName = "Tao";


    public static final String ID=getHLLID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path=loadHaaLouLingCardImg(cardName,type);

    public List<AbstractCard> ExhaustedCards= new ArrayList<AbstractCard>();
    private float rotationTimer;
    private int previewIndex;

    public hllTao() {
        super(ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.NONE);

        this.cardsToPreview=new hllFetter();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!ExhaustedCards.isEmpty())
            for(AbstractCard c : ExhaustedCards)
                addToBot(new PlaySameInstanceAction(c));
    }

    @Override
    public void update() {
        super.update();
        if(!this.hb.hovered){
            rotationTimer=0.0f;
            previewIndex=0;
        }
        else
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 1.0F;
                if (this.ExhaustedCards.isEmpty())
                    this.cardsToPreview = new hllFetter();

                if (this.ExhaustedCards.size() == 1)
                    this.cardsToPreview = this.ExhaustedCards.get(0);

                if (this.ExhaustedCards.size() > 1) {
                    if(previewIndex<this.ExhaustedCards.size())
                        this.cardsToPreview = this.ExhaustedCards.get(this.previewIndex);
                    if (this.previewIndex == this.ExhaustedCards.size() - 1)
                        this.previewIndex = 0;
                    else
                        this.previewIndex++;
                }
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new TaoAction(this));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new hllTao();
    }

    public AbstractCard makeStatEquivalentCopy() {
        hllTao card = (hllTao) super.makeStatEquivalentCopy();
        for(AbstractCard c: ExhaustedCards)
            card.ExhaustedCards.add(c.makeSameInstanceOf());
        return card;
    }
}

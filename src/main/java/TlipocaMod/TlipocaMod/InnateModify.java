package TlipocaMod.TlipocaMod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class InnateModify extends AbstractCardModifier {

    private static final String[] TEXT= CardCrawlGame.languagePack.getUIString("InnateModify").TEXT;


    private void applyModify(final AbstractCard card){
        card.isInnate=true;
    }

    public String modifyDescription(String rawDescription, final AbstractCard card) {
        rawDescription= TEXT[0] + rawDescription;
        return rawDescription;
    }

    public void onInitialApplication(final AbstractCard card) {
        this.applyModify(card);
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new InnateModify();
    }
}

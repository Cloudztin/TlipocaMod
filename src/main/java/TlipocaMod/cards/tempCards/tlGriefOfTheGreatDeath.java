package TlipocaMod.cards.tempCards;

import TlipocaMod.cards.AbstractTlipocaCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static TlipocaMod.TlipocaMod.TlipocaMod.getID;

public class tlGriefOfTheGreatDeath extends AbstractTlipocaCard {

    static final CardRarity rarity = CardRarity.SPECIAL;
    static final CardType type = CardType.CURSE;
    static final int cost = 1;
    static final String cardName = "Grief";


    public static final String ID=getID(cardName);
    private static final CardStrings cardStrings= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String img_path="TlipocaModResources/img/cards/Tlipoca/Grief.png";

    public tlGriefOfTheGreatDeath() {
        super(CardColor.CURSE ,ID, cardStrings.NAME,img_path, cost, cardStrings.DESCRIPTION, type, rarity, CardTarget.NONE);
        SoulboundField.soulbound.set(this, true);
    }



    @Override
    public void upgrade() {
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        addToBot(new MakeTempCardInHandAction(this.makeStatEquivalentCopy(), 1));
    }

    public void triggerOnEndOfTurnForPlayingCard(){
        addToBot(new MakeTempCardInHandAction(this.makeStatEquivalentCopy(), 1));
    }

    public AbstractCard makeCopy() {
        return new tlGriefOfTheGreatDeath();
    }

}

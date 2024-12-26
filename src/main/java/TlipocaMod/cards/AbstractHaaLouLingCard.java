package TlipocaMod.cards;

import TlipocaMod.characters.HaaLouLing;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;

public abstract class AbstractHaaLouLingCard extends CustomCard {

    public Color flavorColor= CardHelper.getColor(111, 255, 195);

    public AbstractHaaLouLingCard(String id, String name, String img_path, int cost, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img_path, cost, description, type, HaaLouLing.PlayerClass.HaaLouLing_Color, rarity,target );

        FlavorText.AbstractCardFlavorFields.boxColor.set(this, flavorColor);
    }

    public AbstractHaaLouLingCard(AbstractCard.CardColor color,String id, String name ,String img_path,  int cost, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target){
        super(id, name, img_path, cost, description, type, color, rarity, target);

        FlavorText.AbstractCardFlavorFields.boxColor.set(this, flavorColor);
    }

    public static String loadHaaLouLingCardImg(String id  ,CardType type) {
        return "TlipocaModResources/img/cards/HaaLouLing/test/"+ type.toString().toLowerCase()+ ".png";
    }

    public void onLock(AbstractCard c) {

    }

    public void onUnlock(AbstractCard c) {

    }

    public void intoLock(int n) {

    }

    public void zeroLock(int n) {

    }
}

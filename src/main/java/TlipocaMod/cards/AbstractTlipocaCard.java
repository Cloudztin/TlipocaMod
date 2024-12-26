package TlipocaMod.cards;

import TlipocaMod.characters.Tlipoca;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;



public abstract class AbstractTlipocaCard extends CustomCard {

    public Color flavorColor= CardHelper.getColor(107, 9, 40);

    public AbstractTlipocaCard(String id, String name,String img_path, int cost, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img_path, cost, description, type, Tlipoca.PlayerClass.Tlipoca_Color, rarity,target );

        FlavorText.AbstractCardFlavorFields.boxColor.set(this, flavorColor);
    }

    public AbstractTlipocaCard(AbstractCard.CardColor color,String id, String name ,String img_path,  int cost, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target){
        super(id, name, img_path, cost, description, type, color, rarity, target);

        FlavorText.AbstractCardFlavorFields.boxColor.set(this, flavorColor);
    }



    public static String loadTlipocaCardImg(String id  ,CardType type) {
        return "TlipocaModResources/img/cards/Tlipoca/test/"+ type.toString().toLowerCase()+ ".png";
    }











}

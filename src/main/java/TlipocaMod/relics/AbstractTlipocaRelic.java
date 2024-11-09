package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class AbstractTlipocaRelic extends CustomRelic {

    public AbstractTlipocaRelic(String id, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, boolean largeIMG){
        super(TlipocaMod.getID(id), new Texture(loadRelicImg(id)) ,tier, sfx );
        this.outlineImg = new Texture( loadOutlineImg(id) );
        if(largeIMG) this.largeImg=new Texture( loadLargeImg(id) );
    }


    private static String loadRelicImg(String id){
        return "TlipocaModResources/img/relics/" + id + ".png";
    }

    private static String loadOutlineImg(String id){
        return "TlipocaModResources/img/relics/outlines/" + id + ".png";
    }

    private static String loadLargeImg(String id){
        return "TlipocaModResources/img/relics/large/" + id + ".png";
    }



}

package TlipocaMod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Type;

public abstract class AbstractTlipocaPower extends TwoAmountPower {

    public AbstractTlipocaPower(String name, String ID, AbstractCreature owner, int amount, PowerType type) {
        this.name = name;
        this.ID = ID;
        this.owner = owner;
        this.amount = amount;
        this.type = type;
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(NormalPowerIMG(this.ID)), 0, 0, 36, 36);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(LargePowerIMG(this.ID)), 0, 0, 72, 72);
    }

    public AbstractTlipocaPower(String name, String ID, AbstractCreature owner, int amount, PowerType type, String imgPath) {
        this.name = name;
        this.ID = ID;
        this.owner = owner;
        this.amount = amount;
        this.type = type;
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(NormalPowerIMG(imgPath)), 0, 0, 36, 36);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(LargePowerIMG(imgPath)), 0, 0, 72, 72);
    }


    public String LargePowerIMG(String PowerID) {
        return "TlipocaModResources/img/powers/Tlipoca/" + PowerID.replace("TlipocaMod:","") + "_p.png";
    }

    public String NormalPowerIMG(String PowerID) {
        return "TlipocaModResources/img/powers/Tlipoca/" + PowerID.replace("TlipocaMod:","") + ".png";
    }

    public void onMonsterDeath(AbstractMonster m){
    }

    public void onRefreshHand(){
    }


    public void onLock(AbstractCard c) {
    }

    public void onUnlock(AbstractCard c) {
    }

    public void onNumUp(AbstractCard c, int n) {

    }

    public void onNumDes(AbstractCard c, int n) {

    }

}

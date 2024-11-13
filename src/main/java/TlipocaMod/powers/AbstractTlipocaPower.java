package TlipocaMod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Type;

public abstract class AbstractTlipocaPower extends TwoAmountPower {

    public AbstractTlipocaPower(String name, String ID, AbstractCreature owner, int amount, PowerType type) {
        this.name = name;
        this.ID = ID;
        this.owner = owner;
        this.amount = amount;
        this.type = type;
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(NormalPowerIMG_beta(this.ID)), 0, 0, 32, 32);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(LargePowerIMG_beta(this.ID)), 0, 0, 64, 64);
    }


    public String LargePowerIMG(String PowerID) {
        return "TlipocaModResources/img/powers/Tlipoca/" + PowerID + "_p.png";
    }

    public String NormalPowerIMG(String PowerID) {
        return "TlipocaModResources/img/powers/Tlipoca/" + PowerID + ".png";
    }

    public String LargePowerIMG_beta(String PowerID) {
        return "TlipocaModResources/img/powers/Tlipoca/test_p.png";
    }

    public String NormalPowerIMG_beta(String PowerID) {
        return "TlipocaModResources/img/powers/Tlipoca/test.png";
    }


    public void onMonsterDeath(AbstractMonster m){
    };

    public void onRefreshHand(){}
}

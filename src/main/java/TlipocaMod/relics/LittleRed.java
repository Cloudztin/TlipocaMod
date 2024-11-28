package TlipocaMod.relics;


import TlipocaMod.TlipocaMod.TlipocaMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LittleRed extends AbstractTlipocaRelic implements CustomSavable<Integer> {

    public static final String relicName = "LittleRed";
    public static final String ID = TlipocaMod.getID(relicName);

    public LittleRed() {
        super(relicName, RelicTier.STARTER, LandingSound.CLINK, true);

        this.counter = 0;
    }

    public void onMonsterDeath(AbstractMonster m){
        if(m.currentHealth <= 0){
            flash();
            this.counter++;
            addToTop(new RelicAboveCreatureAction( m, this));
        }
    }

    public void atTurnStart(){
        if(this.counter > 0){
            this.counter--;
            addToTop(new GainEnergyAction(1));
            addToTop(new RelicAboveCreatureAction( AbstractDungeon.player, this));
        }
    }


    public boolean canSpawn() {
        return false;
    }

    public Integer onSave(){
        return this.counter;
    }

    public void onLoad(Integer balance) {
        if(balance != null){
            if(balance >= 0)
                this.counter = balance;
        }
        else this.counter = 0;
    }






    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LittleRed();
    }
}

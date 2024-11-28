package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BloodyHarvest extends AbstractTlipocaRelic implements CustomSavable<Integer> {
    public static final String relicName = "BloodyHarvest";
    public static final String ID = TlipocaMod.getID(relicName);

    public BloodyHarvest() {
        super(relicName, RelicTier.BOSS, LandingSound.CLINK, true);
        this.counter=0;
    }

    public void onMonsterDeath(AbstractMonster m){
        if ((m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion")){
            flash();
            this.counter+=2;
            addToTop(new RelicAboveCreatureAction( m, this));
        }
    }

    public void atTurnStart(){
        if(this.counter > 0){
            this.counter--;
            addToTop(new GainEnergyAction(1));
            addToTop(new DrawCardAction(2));
            addToTop(new RelicAboveCreatureAction( AbstractDungeon.player, this));
        }
    }


    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(LittleRed.ID);
    }

    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.hasRelic(LittleRed.ID)){
            this.counter+=player.getRelic(LittleRed.ID).counter;
            player.relics.stream()
                    .filter(r -> r instanceof LittleRed).findFirst()
                    .map(r -> Integer.valueOf(player.relics.indexOf(r)))
                    .ifPresent(index -> instantObtain(player, index.intValue(), true));
        }
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
        return new BloodyHarvest();
    }

}

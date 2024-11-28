package TlipocaMod.relics;


import TlipocaMod.TlipocaMod.TlipocaMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class AlchemyFlask extends AbstractTlipocaRelic implements CustomSavable<Integer> {

    public static final String relicName = "AlchemyFlask";
    public static final String ID = TlipocaMod.getID(relicName);

    public AlchemyFlask() {
        super(relicName, RelicTier.UNCOMMON, LandingSound.CLINK, true);

        this.counter = 0;
    }

    public void onMonsterDeath(AbstractMonster m) {
        if(!m.halfDead && !m.hasPower("Minion")){
            flash();
            addToTop(new RelicAboveCreatureAction(m, this));
            this.counter++;
            if(this.counter>=13)
                this.pulse=true;
        }
    }

    public void onVictory() {
        if(this.counter>=13){
            this.counter=0;
            stopPulse();
            int roll = AbstractDungeon.relicRng.random(0, 99);
            if(roll <50) AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.COMMON);
            else if(roll <=85) AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.UNCOMMON);
            else AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.RARE);
            AbstractDungeon.getCurrRoom().addGoldToRewards(77);
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion(AbstractDungeon.miscRng)));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AlchemyFlask();
    }

    public boolean canSpawn() {
        /* 25 */     return (Settings.isEndless || AbstractDungeon.floorNum <= 40);
        /*    */   }

    public Integer onSave() {
        return this.counter;
    }

    public void onLoad(Integer integer) {
        this.counter = integer;
        if(this.counter>=20) this.pulse=true;
    }
}

package TlipocaMod.relics;

import TlipocaMod.TlipocaMod.TlipocaMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HaaLouBlade extends AbstractTlipocaRelic{

    public static final String relicName = "HaaLouBlade";
    public static final String ID = TlipocaMod.getID(relicName);

    public HaaLouBlade() {
        super(relicName, RelicTier.BOSS, LandingSound.CLINK, true);

    }

    @Override
    public void atTurnStart() {
        if(!AbstractDungeon.player.hand.isEmpty()){
            flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2)));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HaaLouBlade();
    }

    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.hasRelic(TangKnife.ID)){
            this.counter+=player.getRelic(TangKnife.ID).counter;
            player.relics.stream()
                    .filter(r -> r instanceof TangKnife).findFirst()
                    .map(r -> player.relics.indexOf(r))
                    .ifPresent(index -> instantObtain(player, index, true));
        }
    }

    public boolean canSpawn(){
        return AbstractDungeon.player.hasRelic(TangKnife.ID);
    }
}

package TlipocaMod.action;

import TlipocaMod.powers.DetectedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class DetectAction extends AbstractGameAction {

    private final AbstractMonster m;
    private final int weakLayer;

    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("DetectAction").TEXT;

    public DetectAction(AbstractMonster m, int weakLayer) {
        this.m = m;
        this.weakLayer = weakLayer;
    }


    public void update() {
        if (this.m != null) {
            if (this.m.getIntentBaseDmg() >= 0) {
                if (!this.m.hasPower(DetectedPower.ID)) {

                    addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
                    addToTop(new ApplyPowerAction(this.m, AbstractDungeon.player, new WeakPower(this.m, weakLayer, false)));
                    addToTop(new ApplyPowerAction(this.m, this.m, new DetectedPower(this.m)));

                    if (!this.m.hasPower("Artifact"))
                        addToTop(new ApplyPowerAction(this.m, AbstractDungeon.player, new GainStrengthPower(this.m, 99), 99));
                    addToTop(new ApplyPowerAction(this.m, AbstractDungeon.player, new StrengthPower(this.m, -99), -99));

                } else
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));

            } else
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }
        this.isDone = true;
    }
}

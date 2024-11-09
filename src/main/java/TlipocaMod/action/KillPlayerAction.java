package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;

import java.util.Iterator;

public class KillPlayerAction extends AbstractGameAction {
    public KillPlayerAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
    }

    public void update() {
        final AbstractPlayer p = AbstractDungeon.player;


        if (!p.hasRelic(MarkOfTheBloom.ID)) {
            if (p.hasPotion(FairyPotion.POTION_ID)) {
                Iterator<AbstractPotion> it16 = p.potions.iterator();
                while (it16.hasNext()) {
                    AbstractPotion potion = it16.next();
                    if (potion.ID.equals(FairyPotion.POTION_ID)) {
                        potion.flash();
                        p.currentHealth = 0;
                        potion.use(p);
                        AbstractDungeon.topPanel.destroyPotion(potion.slot);
                        this.isDone=true;
                        tickDuration();
                        return;
                    }
                }
            } else if (p.hasRelic(LizardTail.ID) && ( p.getRelic(LizardTail.ID)).counter == -1) {
                p.currentHealth = 0;
                p.getRelic(LizardTail.ID).onTrigger();
                this.isDone=true;
                tickDuration();
                return;
            }
        }
        p.isDead = true;
        AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        p.currentHealth = 0;
        if (p.currentBlock > 0) {
            p.loseBlock();
            AbstractDungeon.effectList.add(new HbBlockBrokenEffect(p.hb.cX - p.hb.width / 2.0f + -14.0f * Settings.scale, p.hb.cY - p.hb.height / 2.0f + -14.0f * Settings.scale));
        }

        this.isDone=true;
        return;
    }

}

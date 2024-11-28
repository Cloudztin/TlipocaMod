package TlipocaMod.patches;

import TlipocaMod.relics.AbstractTlipocaRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RelicPatch {

    @SpirePatch(clz= AbstractMonster.class, method = "damage")
    public static class BleedPatch {
        @SpireInsertPatch(rloc=73)
        public static void Insert(AbstractMonster monster, DamageInfo info) {
            if(info.type != DamageInfo.DamageType.NORMAL)
                for(AbstractRelic r: AbstractDungeon.player.relics)
                    if(r instanceof AbstractTlipocaRelic)
                        ((AbstractTlipocaRelic) r).onMonsterDamaged();
        }
    }
}

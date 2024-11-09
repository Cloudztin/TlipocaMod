package TlipocaMod.patches;

import TlipocaMod.powers.AbstractTlipocaPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class PowerPatch {

    @SpirePatch(clz= AbstractMonster.class, method = "die", paramtypez={boolean.class})
    public static class DeathPatch1{
        @SpireInsertPatch(rloc = 9)
        public static void Insert(AbstractMonster mo) {
            for(AbstractPower p: AbstractDungeon.player.powers)
                if(p instanceof AbstractTlipocaPower)
                    ((AbstractTlipocaPower)p).onMonsterDeath(mo);
        }
    }
}

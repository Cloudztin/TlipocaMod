package TlipocaMod.patches;

import TlipocaMod.cards.rare.tlExecution;
import TlipocaMod.cards.rare.tlJustitia;
import TlipocaMod.cards.uncommon.hllHaaLouTreasure;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import javassist.CtBehavior;

public class MiscPatch {
    @SpirePatch(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
    public static class loadMiscPatch {
        @SpireInsertPatch(locator = Locator.class, localvars = {"retVal"})
        public static void LoadMisc(final String key, final int upgradeTime, final int misc, final AbstractCard retVal) {
            if (retVal.cardID.equals(tlExecution.ID) && misc!=0) {
                retVal.baseMagicNumber+=misc;
                retVal.magicNumber+=misc;
            }
            if (retVal.cardID.equals(hllHaaLouTreasure.ID) && misc != 0) {
                retVal.updateCost(-misc);
                retVal.initializeDescription();
            }
        }

        @SpireInsertPatch(locator = Locator.class, localvars = {"retVal"})
        public static void Insert(final String key, final int upgradeTime, final int misc, final AbstractCard retVal) {
            if (retVal.cardID.equals(tlJustitia.ID) && misc!=0) {
                retVal.updateCost(-misc);
                retVal.initializeDescription();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(final CtBehavior ctMethodToPatch) throws Exception {
                final Matcher finalMatcher = (Matcher) new Matcher.FieldAccessMatcher((Class) AbstractCard.class, "misc");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}


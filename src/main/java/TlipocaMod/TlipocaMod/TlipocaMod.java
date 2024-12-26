package TlipocaMod.TlipocaMod;


import TlipocaMod.characters.HaaLouLing;
import TlipocaMod.characters.Tlipoca;
import TlipocaMod.patches.CardPatch;
import TlipocaMod.potions.*;
import TlipocaMod.relics.*;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SpireInitializer
public class TlipocaMod implements PostInitializeSubscriber, EditCharactersSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, AddAudioSubscriber {

    public static final Color Tlipoca_Color = CardHelper.getColor(255, 13, 102);
    public static final Color HaaLouLing_Color = CardHelper.getColor(34, 176, 133);
    public static final String ModID = "TlipocaMod";
    public static String lang;
    private final String[] langSupported = {"zhs", "eng"};

    public static boolean tutorial = false;

    public TlipocaMod() {
        BaseMod.subscribe( this);
        BaseMod.addColor(Tlipoca.PlayerClass.Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, "TlipocaModResources/img/basic/512/attack.png", "TlipocaModResources/img/basic/512/skill.png", "TlipocaModResources/img/basic/512/power.png", "TlipocaModResources/img/basic/512/orb.png", "TlipocaModResources/img/basic/1024/attack.png", "TlipocaModResources/img/basic/1024/skill.png", "TlipocaModResources/img/basic/1024/power.png", "TlipocaModResources/img/basic/1024/orb.png", "TlipocaModResources/img/basic/ORB.png");
        BaseMod.addColor(HaaLouLing.PlayerClass.HaaLouLing_Color, HaaLouLing_Color, HaaLouLing_Color, HaaLouLing_Color, HaaLouLing_Color, HaaLouLing_Color, HaaLouLing_Color, HaaLouLing_Color, "TlipocaModResources/img/basic/512/attackHLL.png", "TlipocaModResources/img/basic/512/skillHLL.png", "TlipocaModResources/img/basic/512/powerHLL.png", "TlipocaModResources/img/basic/512/orbHLL.png", "TlipocaModResources/img/basic/1024/attackHLL.png", "TlipocaModResources/img/basic/1024/skillHLL.png", "TlipocaModResources/img/basic/1024/powerHLL.png", "TlipocaModResources/img/basic/1024/HaaLouLingOrb.png", "TlipocaModResources/img/basic/orbHLL.png");
    }

    public static String getID(String id) {
        return "TlipocaMod:" + id;
    }

    public static String getHLLID(String id) {
        return "HaaLouLing:" + id;
    }

    public static void initialize() {
        new TlipocaMod();
        try {
            Properties defaults = new Properties();
            SpireConfig config = new SpireConfig("TlipocaMod","common", defaults);
            tutorial=config.getBool(getID("tutorial"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveAddAudio() {
    }

    public void receivePostInitialize() {
        Texture badgeTexture = new Texture("TlipocaModResources/img/others/TlipocaBadge.png");
        BaseMod.registerModBadge(badgeTexture, "Tlipoca Mod", "de Fina", "v0.1.0\n Add a new character.", null);
        receiveEditPotions();
        loadSavedCardCost();
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }

    public void receiveEditCharacters() {
        BaseMod.addCharacter( new Tlipoca("Tlipoca"), "TlipocaModResources/img/others/TlipocaButton.png", "TlipocaModResources/img/others/TlipocaStartBG.png", Tlipoca.PlayerClass.Tlipoca);
        BaseMod.addCharacter(new HaaLouLing("HaaLouLing"), "TlipocaModResources/img/others/HaaLouLingButton.png", "TlipocaModResources/img/others/HaaLouLingStartBG.png", HaaLouLing.PlayerClass.HaaLouLing);
    }

    public void receiveEditRelics() {

        BaseMod.addRelicToCustomPool(new LittleRed(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new NightCrown(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new BloodyHarvest(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new CassildasSong(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new DragonBlood(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new Cryolite(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new SoulStone(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new AlchemyFlask(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new SealofthePast(), Tlipoca.PlayerClass.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new Schistosome(), Tlipoca.PlayerClass.Tlipoca_Color);

        BaseMod.addRelicToCustomPool(new BurrPuzzle(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new HaaLouBlade(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new Horoscope(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new ShackleOfDream(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new SpiritSeveranceTome(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new TangKnife(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new theRecipe(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new theUnknown(), HaaLouLing.PlayerClass.HaaLouLing_Color);
        BaseMod.addRelicToCustomPool(new QuetzalcoatlsFeather(), HaaLouLing.PlayerClass.HaaLouLing_Color);

        BaseMod.addRelic(new Revelation(), RelicType.SHARED);
        BaseMod.addRelic(new ShoggothBlood(), RelicType.SHARED);

        UnlockTracker.markRelicAsSeen(LittleRed.ID);
        UnlockTracker.markRelicAsSeen(NightCrown.ID);
        UnlockTracker.markRelicAsSeen(BloodyHarvest.ID);
        UnlockTracker.markRelicAsSeen(CassildasSong.ID);
        UnlockTracker.markRelicAsSeen(DragonBlood.ID);
        UnlockTracker.markRelicAsSeen(Cryolite.ID);
        UnlockTracker.markRelicAsSeen(SoulStone.ID);
        UnlockTracker.markRelicAsSeen(AlchemyFlask.ID);
        UnlockTracker.markRelicAsSeen(SealofthePast.ID);
        UnlockTracker.markRelicAsSeen(Schistosome.ID);
        UnlockTracker.markRelicAsSeen(Revelation.ID);
        UnlockTracker.markRelicAsSeen(BurrPuzzle.ID);
        UnlockTracker.markRelicAsSeen(HaaLouBlade.ID);
        UnlockTracker.markRelicAsSeen(Horoscope.ID);
        UnlockTracker.markRelicAsSeen(ShoggothBlood.ID);
        UnlockTracker.markRelicAsSeen(ShackleOfDream.ID);
        UnlockTracker.markRelicAsSeen(SpiritSeveranceTome.ID);
        UnlockTracker.markRelicAsSeen(TangKnife.ID);
        UnlockTracker.markRelicAsSeen(theRecipe.ID);
        UnlockTracker.markRelicAsSeen(theUnknown.ID);
        UnlockTracker.markRelicAsSeen(QuetzalcoatlsFeather.ID);
    }

    public void receiveEditPotions() {
        BaseMod.addPotion(CorrectionFluid.class, null, null, null, CorrectionFluid.POTION_ID, Tlipoca.PlayerClass.Tlipoca);
        BaseMod.addPotion(BottledDisease.class,BottledDisease.liquidColor, BottledDisease.hybridColor, BottledDisease.SpotsColor, BottledDisease.POTION_ID, Tlipoca.PlayerClass.Tlipoca);
        BaseMod.addPotion(InfinityPotion.class, InfinityPotion.liquidColor, InfinityPotion.hybridColor, InfinityPotion.SpotsColor, InfinityPotion.POTION_ID, Tlipoca.PlayerClass.Tlipoca);
        BaseMod.addPotion(MasterPotion.class, MasterPotion.liquidColor, MasterPotion.hybridColor, MasterPotion.SpotsColor, MasterPotion.POTION_ID, HaaLouLing.PlayerClass.HaaLouLing);
        BaseMod.addPotion(IngeniousPotion.class, IngeniousPotion.liquidColor, IngeniousPotion.hybridColor, IngeniousPotion.SpotsColor, IngeniousPotion.POTION_ID, HaaLouLing.PlayerClass.HaaLouLing);
        BaseMod.addPotion(SpringWater.class, Color.CLEAR.cpy(),  Color.CLEAR.cpy(), Color.CLEAR.cpy(), SpringWater.POTION_ID, HaaLouLing.PlayerClass.HaaLouLing);

    }

    public void receiveEditCards() {
        System.out.println("Adding cards");
        new AutoAdd("TlipocaMod").packageFilter("TlipocaMod.cards").notPackageFilter("TlipocaMod.cards.deprecated").notPackageFilter("TlipocaMod.cards.choices").notPackageFilter("TlipocaMod.cards.special").setDefaultSeen(true).cards();
        new AutoAdd("TlipocaMod").packageFilter("TlipocaMod.cards.special").cards();
    }

    @Override
    public void receiveEditStrings() {
        lang = Settings.language.toString().toLowerCase();
        if (!Arrays.asList(langSupported).contains(lang))
            lang = "eng";
        String relic = "", card = "", power = "", text = "", potion = "";
        card = "TlipocaModResources/localization/"+ lang +"/cards.json";
        power = "TlipocaModResources/localization/" + lang + "/powers.json";
        relic = "TlipocaModResources/localization/" + lang + "/relics.json";
        text = "TlipocaModResources/localization/" + lang + "/UIStrings.json";
        potion = "TlipocaModResources/localization/" + lang + "/potions.json";

        BaseMod.loadCustomStringsFile(RelicStrings.class, relic);
        BaseMod.loadCustomStringsFile(CardStrings.class, card);
        BaseMod.loadCustomStringsFile(PowerStrings.class, power);
        BaseMod.loadCustomStringsFile(UIStrings.class, text);
        BaseMod.loadCustomStringsFile(PotionStrings.class, potion);
    }


    @Override
    public void receiveEditKeywords() {
        String keywordsPath;


        lang = Settings.language.toString().toLowerCase();
        if (!Arrays.asList(langSupported).contains(lang))
            lang = "eng";
        keywordsPath = "TlipocaModResources/localization/" + lang + "/keywords.json";
        Gson gson = new Gson();
        Keyword[] keywords = gson.fromJson(loadJson(keywordsPath), Keyword[].class);
        if (keywords != null) for (Keyword keyword : keywords)
                BaseMod.addKeyword("tlipocamod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);


    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }


    private void loadSavedCardCost(){
        BaseMod.addSaveField("deckCost", new CustomSavable<List<CardPatch.cardCostUnit>>() {
            public List<CardPatch.cardCostUnit> onSave() {
                List<CardPatch.cardCostUnit> result = new ArrayList<>();
                for(AbstractCard c:AbstractDungeon.player.masterDeck.group){
                    CardPatch.cardCostUnit tmp = new CardPatch.cardCostUnit();
                    tmp.index= AbstractDungeon.player.masterDeck.group.indexOf(c);
                    tmp.updated=CardPatch.newVarField.updated.get(c);
                    tmp.eternalCost =CardPatch.newVarField.eternalCost.get(c);
                    tmp.eternity=CardPatch.newVarField.eternity.get(c);
                    result.add(tmp);
                }
                return result;
            }

            public void onLoad(List<CardPatch.cardCostUnit> deckCost) {
                if(deckCost!=null)
                    if(!deckCost.isEmpty())
                        for(CardPatch.cardCostUnit unit:deckCost){
                            if(unit.index>0 &&unit.index<AbstractDungeon.player.masterDeck.size()){
                                AbstractCard c=AbstractDungeon.player.masterDeck.group.get(unit.index);
                                CardPatch.newVarField.updated.set(c,unit.updated);
                                CardPatch.newVarField.eternity.set(c,unit.eternity);
                                CardPatch.newVarField.eternalCost.set(c,unit.eternalCost);
                                if(unit.eternity)
                                    c.costForTurn=unit.eternalCost;
                            }
                        }
            }

        });
    }



}

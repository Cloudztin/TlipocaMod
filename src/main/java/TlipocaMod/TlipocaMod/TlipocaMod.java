package TlipocaMod.TlipocaMod;


import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.characters.Tlipoca;
import TlipocaMod.enums.CardEnum;
import TlipocaMod.enums.CharacterEnum;
import TlipocaMod.enums.ModClassEnum;
import TlipocaMod.patches.CardPatch;
import TlipocaMod.potions.BottledDisease;
import TlipocaMod.potions.CorrectionFluid;
import TlipocaMod.potions.InfinityPotion;
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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
    public static final String ModID = "TlipocaMod";
    public static String lang;
    private final String[] langSupported = {"zhs", "eng"};

    public static boolean tutorial = false;

    public TlipocaMod() {
        BaseMod.subscribe( this);
        BaseMod.addColor(CardEnum.Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, "TlipocaModResources/img/basic/512/attack.png", "TlipocaModResources/img/basic/512/skill.png", "TlipocaModResources/img/basic/512/power.png", "TlipocaModResources/img/basic/512/orb.png", "TlipocaModResources/img/basic/1024/attack.png", "TlipocaModResources/img/basic/1024/skill.png", "TlipocaModResources/img/basic/1024/power.png", "TlipocaModResources/img/basic/1024/orb.png", "TlipocaModResources/img/basic/ORB.png");
    }

    public static String getID(String id) {
        return "TlipocaMod:" + id;
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
        BaseMod.addCharacter((AbstractPlayer) new Tlipoca("Tlipoca"), "TlipocaModResources/img/others/TlipocaButton.png", "TlipocaModResources/img/others/TlipocaStartBG.png", ModClassEnum.Tlipoca_Color);
    }

    public void receiveEditRelics() {

        BaseMod.addRelicToCustomPool(new LittleRed(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new NightCrown(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new BloodyHarvest(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new CassildasSong(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new DragonBlood(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new Cryolite(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new SoulStone(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new AlchemyFlask(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new SealofthePast(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new Schistosome(), CardEnum.Tlipoca_Color);

        BaseMod.addRelic(new Revelation(), RelicType.SHARED);

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
    }

    public void receiveEditPotions() {
        BaseMod.addPotion(CorrectionFluid.class, null, null, null, CorrectionFluid.POTION_ID, CharacterEnum.Tlipoca);
        BaseMod.addPotion(BottledDisease.class,BottledDisease.liquidColor, BottledDisease.hybridColor, BottledDisease.SpotsColor, BottledDisease.POTION_ID, CharacterEnum.Tlipoca);
        BaseMod.addPotion(InfinityPotion.class, InfinityPotion.liquidColor, InfinityPotion.hybridColor, InfinityPotion.SpotsColor, InfinityPotion.POTION_ID, CharacterEnum.Tlipoca);
    }

    public void receiveEditCards() {
        System.out.println("Adding cards");
        new AutoAdd("TlipocaMod").packageFilter(AbstractTlipocaCard.class).notPackageFilter("TlipocaMod.cards.deprecated").setDefaultSeen(true).cards();
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

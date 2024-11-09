package TlipocaMod.TlipocaMod;


import TlipocaMod.cards.AbstractTlipocaCard;
import TlipocaMod.characters.Tlipoca;
import TlipocaMod.enums.CardEnum;
import TlipocaMod.enums.ModClassEnum;
import TlipocaMod.patches.CardPatch;
import TlipocaMod.relics.BloodyHarvest;
import TlipocaMod.relics.LittleRed;
import TlipocaMod.relics.NightCrown;
import TlipocaMod.relics.Revelation;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.DevConsole;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpireInitializer
public class TlipocaMod implements PostInitializeSubscriber, EditCharactersSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, AddAudioSubscriber {

    public static final Color Tlipoca_Color = CardHelper.getColor(255, 13, 102);

    public static final Color PColor = new Color(0.25F, 0.286F, 0.541F, 1.0F);


    public static String lang;

    private final String[] langSupported = {"zhs", "eng"};


    public static ArrayList<AbstractCard> Tlipoca_Cards = new ArrayList<>();

    public TlipocaMod() {
        BaseMod.subscribe((ISubscriber) this);
        BaseMod.addColor(CardEnum.Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, Tlipoca_Color, "TlipocaModResources/img/basic/512/attack.png", "TlipocaModResources/img/basic/512/skill.png", "TlipocaModResources/img/basic/512/power.png", "TlipocaModResources/img/basic/512/orb.png", "TlipocaModResources/img/basic/1024/attack.png", "TlipocaModResources/img/basic/1024/skill.png", "TlipocaModResources/img/basic/1024/power.png", "TlipocaModResources/img/basic/1024/orb.png", "TlipocaModResources/img/basic/ORB.png");
    }

    public static String getID(String id) {
        return "TlipocaMod:" + id;
    }

    public static void initialize() {
        DevConsole.logger.info("5");
        new TlipocaMod();
        DevConsole.logger.info("6");
    }

    public void receiveAddAudio() {
    }

    public void receivePostInitialize() {
        Texture badgeTexture = new Texture("TlipocaModResources/img/basic/ORB.png");
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, "Tlipoca Mod", "de Fina", "v0.1.0\n Add a new player.", settingsPanel);
        loadSavedCardCost();
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }

    public void receiveEditCharacters() {
        DevConsole.logger.info("7");
        BaseMod.addCharacter((AbstractPlayer) new Tlipoca("Tlipoca"), "TlipocaModResources/img/others/TlipocaButton.png", "TlipocaModResources/img/others/TlipocaStartBG.png", ModClassEnum.Tlipoca_Color);
    }

    public void receiveEditRelics() {

        BaseMod.addRelicToCustomPool(new LittleRed(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new NightCrown(), CardEnum.Tlipoca_Color);
        BaseMod.addRelicToCustomPool(new BloodyHarvest(), CardEnum.Tlipoca_Color);

        BaseMod.addRelic(new Revelation(), RelicType.SHARED);
    }

    public void receiveEditCards() {
        System.out.println("Adding cards");
        new AutoAdd("TlipocaMod").packageFilter(AbstractTlipocaCard.class).notPackageFilter("TlipocaMod/cards/deprecated").setDefaultSeen(true).cards();
    }

    @Override
    public void receiveEditStrings() {
        lang = Settings.language.toString().toLowerCase();
        if (!Arrays.asList(langSupported).contains(lang))
            lang = "eng";
        String relic = "", card = "", power = "", text = ""; //potion = ""
        card = "TlipocaModResources/localization/"+ lang +"/cards.json";
        power = "TlipocaModResources/localization/" + lang + "/powers.json";
        relic = "TlipocaModResources/localization/" + lang + "/relics.json";
        text = "TlipocaModResources/localization/" + lang + "/UIStrings.json";

        BaseMod.loadCustomStringsFile(RelicStrings.class, relic);
        BaseMod.loadCustomStringsFile(CardStrings.class, card);
        BaseMod.loadCustomStringsFile(PowerStrings.class, power);
        BaseMod.loadCustomStringsFile(UIStrings.class, text);
    }

    class Keywords {
        Keyword[] keywords;
    }

    @Override
    public void receiveEditKeywords() {
        String keywordsPath;
        int var6;
        lang = Settings.language.toString().toLowerCase();
        if (!Arrays.asList(langSupported).contains(lang))
            lang = "eng";
        keywordsPath = "TlipocaModResources/localization/" + lang + "/keywords.json";
        Gson gson = new Gson();
        Keywords keywords = gson.fromJson(loadJson(keywordsPath), Keywords.class);
        Keyword[] var4 = keywords.keywords;
        int var5 = var4.length;
        for (var6 = 0; var6 < var5; var6++) {
            Keyword key = var4[var6];
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
        }

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
                    tmp.index=Integer.valueOf(AbstractDungeon.player.masterDeck.group.indexOf(c));
                    tmp.updated=CardPatch.newVarField.updated.get(c);
                    tmp.eternelCost=CardPatch.newVarField.eternelCost.get(c);
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
                                CardPatch.newVarField.eternelCost.set(c,unit.eternelCost);
                                if(unit.eternity)
                                    c.costForTurn=unit.eternelCost;
                            }
                        }
            }

        });
    }



}

package TlipocaMod.characters;

import TlipocaMod.cards.basic.tlDeterrent;
import TlipocaMod.enums.CardEnum;
import TlipocaMod.enums.ModClassEnum;
import TlipocaMod.relics.LittleRed;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import java.util.ArrayList;
import java.util.List;

public class Tlipoca extends CustomPlayer {

    //初始能量
    public static final String[] orbTextures = new String[]{"TlipocaModResources/img/energy/layer23.png", "TlipocaModResources/img/energy/layer22.png", "TlipocaModResources/img/energy/layer21.png", "TlipocaModResources/img/energy/layer20.png", "TlipocaModResources/img/energy/layer19.png", "TlipocaModResources/img/energy/layer18.png", "TlipocaModResources/img/energy/layer23d.png", "TlipocaModResources/img/energy/layer22d.png", "TlipocaModResources/img/energy/layer21d.png", "TlipocaModResources/img/energy/layer20d.png", "TlipocaModResources/img/energy/layer19d.png"};
    public static AnimationState.TrackEntry e;
    private static final float[] LAYER_SPEED = new float[]{-32.0F, -16.0F, 64.0F, 32.0F, 64.0F, -64.0F, -64.0F, 64.0F, -30.0F, 30.0F};
    private static final int STARTING_HP = 72;
    private static final int MAX_HP = 72;
    private static final int STARTING_GOLD = 99;
    private static final int MAX_ORBS = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 6;
    public static final int CARD_DRAW = 5;
    //返回一个颜色
    public static final Color TlipocaColor = CardHelper.getColor(255, 13, 102);
    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("Tlipoca").TEXT;
    }

    public Tlipoca(String name) {
        super(name, ModClassEnum.Tlipoca_Color, orbTextures, "TlipocaModResources/img/energy/energyTLVFX.png", LAYER_SPEED, null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 240.0F * Settings.scale;

        initializeClass(null, "TlipocaModResources/img/others/TlipocaShoulder.png", "TlipocaModResources/img/others/TlipocaShoulder.png", null, getLoadout(), 0.0F, -30.0F, 280.0F, 366.0F, new EnergyManager(3));
        loadAnimation("TlipocaModResources/img/spines/Tlipoca/Tlipoca.atlas", "TlipocaModResources/img/spines/Tlipoca/Tlipoca.json", 1.0F);
        e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.0F);
    }

    public static class Enums {

        @SpireEnum
        public static AbstractPlayer.PlayerClass Tlipoca;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        //添加初始卡组
        ArrayList<String> deck = new ArrayList<>();
        for(int i=0; i<4; i++)
            deck.add("TlipocaMod:Strike");
        for(int i=0; i<4; i++)
            deck.add("TlipocaMod:Defend");
        deck.add("TlipocaMod:Deterrent");
        deck.add("TlipocaMod:SoulStrike");
        return deck;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //add the special card in the starter's deck
        return new tlDeterrent();
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        //添加初始遗物
        ArrayList<String> relics = new ArrayList<>();
        relics.add(LittleRed.ID);
        return relics;
    }

    @Override
    public CharSelectInfo getLoadout() {
        //选人界面的文字描述
        String title = "";
        String flavor = "";
        title = TEXT[0];
        flavor = TEXT[1];

        return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP, MAX_ORBS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return TEXT[0];
    }

    public AbstractCard.CardColor getCardColor() {
        //选择卡牌颜色
        return CardEnum.Tlipoca_Color;
    }

    @Override
    public Color getCardRenderColor() {
        return TlipocaColor;
    }

    @Override
    public Color getCardTrailColor() {
        return TlipocaColor;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        return;
    }

    @Override
    public void updateOrb(int orbCount) {
        this.energyOrb.updateOrb(orbCount);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        return TEXT[0];
    }

    @Override
    public void playDeathAnimation() {
        e = this.state.setAnimation(0, "Sleep", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.0F);
    }

    @Override
    public AbstractPlayer newInstance() {
        return  new Tlipoca(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[2];
    }

    @Override
    public Color getSlashAttackColor() {
        return TlipocaColor;
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }

    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("TlipocaModResources/img/others/TlipocaVictory.png"));
        return panels;
    }
}

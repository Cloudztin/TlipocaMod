package TlipocaMod.characters;

import TlipocaMod.cards.basic.hllFirmness;
import TlipocaMod.relics.SpiritSeveranceTome;
import TlipocaMod.relics.TangKnife;
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
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class HaaLouLing extends CustomPlayer {

    public static final String[] orbTextures = new String[]{"TlipocaModResources/img/energy/layer1.png", "TlipocaModResources/img/energy/layer2.png", "TlipocaModResources/img/energy/layer3.png", "TlipocaModResources/img/energy/layer4.png", "TlipocaModResources/img/energy/layer5.png", "TlipocaModResources/img/energy/layer6.png", "TlipocaModResources/img/energy/layer1d.png", "TlipocaModResources/img/energy/layer2d.png", "TlipocaModResources/img/energy/layer3d.png", "TlipocaModResources/img/energy/layer4d.png", "TlipocaModResources/img/energy/layer5d.png"};

    public static AnimationState.TrackEntry e;
    private static final float[] LAYER_SPEED = new float[]{-32.0F, -16.0F, 64.0F, 32.0F, 64.0F, -64.0F, -64.0F, 64.0F, -30.0F, 30.0F};
    private static final int STARTING_HP = 70;
    private static final int MAX_HP = 70;
    private static final int STARTING_GOLD = 99;
    private static final int MAX_ORBS = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 6;
    public static final int CARD_DRAW = 5;
    public static final Color HaaLouLingColor = CardHelper.getColor(34, 176, 133);
    public static final String[] TEXT;

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("HaaLouLing").TEXT;
    }

    public static class PlayerClass{
        @SpireEnum(name="HaaLouLing_Color")
        public static AbstractCard.CardColor HaaLouLing_Color;

        @SpireEnum
        public static AbstractPlayer.PlayerClass HaaLouLing;

        @SpireEnum(name="HaaLouLing_Color")
        public static CardLibrary.LibraryType HaaLouLing_Libarary;
    }

    public HaaLouLing(String name) {
        super(name, PlayerClass.HaaLouLing, orbTextures, "TlipocaModResources/img/energy/energyHaaLouLingVFX.png", LAYER_SPEED, null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 240.0F * Settings.scale;

        initializeClass(null, "TlipocaModResources/img/others/HaaLouLingShoulder.png", "TlipocaModResources/img/others/HaaLouLingShoulder.png", null, getLoadout(), 0.0F, -30.0F, 280.0F, 366.0F, new EnergyManager(3));
        loadAnimation("TlipocaModResources/img/spines/HaaLouLing/Xialuling.atlas", "TlipocaModResources/img/spines/HaaLouLing/Xialuling.json", 1.0F);
        e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.0F);
    }

    @Override
    public AbstractCard getStartCardForEvent() {


        return new hllFirmness();
    }

    @Override
    public ArrayList<String> getStartingDeck(){
        ArrayList<String> deck=new ArrayList<>();

        for(int i=0; i<4; i++)
            deck.add("HaaLouLing:Strike");
        for(int i=0; i<4; i++)
            deck.add("HaaLouLing:Defend");
        deck.add("HaaLouLing:Suburi");
        deck.add("HaaLouLing:Firmness");

        return deck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> relics = new ArrayList<>();

        relics.add(SpiritSeveranceTome.ID);
        relics.add(TangKnife.ID);
        return relics;
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
    public CharSelectInfo getLoadout() {
        String title = "";
        String flavor = "";
        title = TEXT[0];
        flavor = TEXT[1];

        return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP, MAX_ORBS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return TEXT[0];
    }

    public AbstractCard.CardColor getCardColor() {
        //选择卡牌颜色
        return PlayerClass.HaaLouLing_Color;
    }

    @Override
    public Color getCardRenderColor() {
        return HaaLouLingColor;
    }

    @Override
    public Color getCardTrailColor(){
        return HaaLouLingColor;
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
        return new HaaLouLing(name);
    }
    @Override
    public String getSpireHeartText() {
        return TEXT[2];
    }

    @Override
    public Color getSlashAttackColor() {
        return HaaLouLingColor;
    }


}

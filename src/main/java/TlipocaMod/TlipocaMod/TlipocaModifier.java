package TlipocaMod.TlipocaMod;

import TlipocaMod.patches.CardPatch;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;


@AbstractCardModifier.SaveIgnore
public class TlipocaModifier extends AbstractCardModifier {

    private static final String[] TEXT= CardCrawlGame.languagePack.getUIString("TlipocaModifier").TEXT;
    public static String ID;
    public ArrayList<supportedModify> extraModify;
    public ArrayList<supportedModify> extraModifyForThisTurn;


    public TlipocaModifier() {
        extraModify=new ArrayList<supportedModify>();
        extraModifyForThisTurn=new ArrayList<supportedModify>();
    }

    public TlipocaModifier( supportedModify modify, boolean onlyThisTurn){
        this();
        if(onlyThisTurn)
            this.extraModifyForThisTurn.add(modify);
        else
            this.extraModify.add(modify);
    }

    public void addModify(final supportedModify modify, final boolean onlyThisTurn){
        if(onlyThisTurn){
            if(this.extraModifyForThisTurn.contains(modify)|| this.extraModify.contains(modify))
                return;
            this.extraModifyForThisTurn.add(modify);
        }
        else if(!this.extraModify.contains(modify))
            this.extraModify.add(modify);
    }

    private void applyModify(final AbstractCard card){
        if(this.extraModifyForThisTurn.contains(supportedModify.TWINCAST) ||this.extraModify.contains(supportedModify.TWINCAST))
            CardPatch.newVarField.twinCast.set(card,true);
        if(this.extraModify.contains(supportedModify.ETERNITY) ||this.extraModifyForThisTurn.contains(supportedModify.ETERNITY))
            CardPatch.newVarField.eternity.set(card,true);
        if(this.extraModify.contains(supportedModify.EPHEMERAL) ||this.extraModifyForThisTurn.contains(supportedModify.EPHEMERAL))
            CardPatch.newVarField.ephemeral.set(card,true);
        if(this.extraModifyForThisTurn.contains(supportedModify.RESONATE) ||this.extraModify.contains(supportedModify.RESONATE))
            CardPatch.newVarField.resonate.set(card,true);

        card.initializeDescription();
    }


    public List<TooltipInfo> additionalTooltips(final AbstractCard card) {
        if(this.extraModify.isEmpty() && this.extraModifyForThisTurn.isEmpty())
            return null;
        final StringBuilder description = new StringBuilder();
        if(!this.extraModify.isEmpty()){
            description.append(TEXT[9]);
            keywordBuilder(description, this.extraModify);
            if(!this.extraModifyForThisTurn.isEmpty()){
                description.append(TEXT[3]);
                description.append(TEXT[7]);
                description.append(TEXT[10]);
                keywordBuilder(description, this.extraModifyForThisTurn);
            }
        }
        else {
            description.append(TEXT[10]);
            keywordBuilder(description, this.extraModifyForThisTurn);
        }
        final TooltipInfo ModifyTip = new TooltipInfo(TEXT[8], description.toString());
        final List<TooltipInfo> list = new ArrayList<>();
        list.add(ModifyTip);
        return list;
    }



    private static void keywordBuilder(final StringBuilder description, final ArrayList<supportedModify> modifies){
        int counter=0;
        if(modifies.contains(supportedModify.TWINCAST)){
            description.append(TEXT[6]);
            description.append(TEXT[0]);
            ++counter;
            if(modifies.size()<=counter){
                description.append(TEXT[5]);
                return;
            }
            description.append(TEXT[4]);
        }
        if(modifies.contains(supportedModify.EPHEMERAL)){
            description.append(TEXT[6]);
            description.append(TEXT[1]);
            ++counter;
            if(modifies.size()<=counter){
                description.append(TEXT[5]);
                return;
            }
            description.append(TEXT[4]);
        }
        if(modifies.contains(supportedModify.ETERNITY)){
            description.append(TEXT[6]);
            description.append(TEXT[2]);
            ++counter;
            if(modifies.size()<=counter){
                description.append(TEXT[5]);
                return;
            }
            description.append(TEXT[4]);
            }
        if(modifies.contains(supportedModify.RESONATE)){
            description.append(TEXT[6]);
            description.append(TEXT[11]);
            description.append(TEXT[5]);
        }
    }

    public String modifyDescription(String rawDescription, final AbstractCard card){
        final StringBuilder thingsToAdd = new StringBuilder();
        if(this.extraModify.contains(supportedModify.TWINCAST) ||this.extraModifyForThisTurn.contains(supportedModify.TWINCAST)){
            thingsToAdd.append(TEXT[12]);
            thingsToAdd.append(TEXT[0]);
            thingsToAdd.append(TEXT[5]);
            thingsToAdd.append(TEXT[3]);
        }
        if(this.extraModify.contains(supportedModify.EPHEMERAL) ||this.extraModifyForThisTurn.contains(supportedModify.EPHEMERAL) ){
            thingsToAdd.append(TEXT[12]);
            thingsToAdd.append(TEXT[1]);
            thingsToAdd.append(TEXT[5]);
            thingsToAdd.append(TEXT[3]);
        }
        if(this.extraModify.contains(supportedModify.ETERNITY) ||this.extraModifyForThisTurn.contains(supportedModify.ETERNITY) ){
            thingsToAdd.append(TEXT[12]);
            thingsToAdd.append(TEXT[2]);
            thingsToAdd.append(TEXT[5]);
            thingsToAdd.append(TEXT[3]);
        }
        if(this.extraModify.contains(supportedModify.RESONATE) ||this.extraModifyForThisTurn.contains(supportedModify.RESONATE) ){
            thingsToAdd.append(TEXT[12]);
            thingsToAdd.append(TEXT[11]);
            thingsToAdd.append(TEXT[5]);
            thingsToAdd.append(TEXT[3]);
        }
        if (thingsToAdd.length() > 0){
            thingsToAdd.append(TEXT[7]);
            rawDescription =thingsToAdd + rawDescription;
        }
        return rawDescription;
    }


    public boolean shouldApply(final AbstractCard card) {
        final ArrayList<AbstractCardModifier> mods = (ArrayList<AbstractCardModifier>) CardModifierManager.getModifiers(card, TlipocaModifier.ID);
        if (!mods.isEmpty() && mods.get(0) instanceof TlipocaModifier) {
            for (final supportedModify p : this.extraModifyForThisTurn) {
                ((TlipocaModifier) mods.get(0)).addModify(p, true);
            }
            for (final supportedModify p : this.extraModify) {
                ((TlipocaModifier) mods.get(0)).addModify(p, false);
            }
            ((TlipocaModifier) mods.get(0)).applyModify(card);
            return false;
        }
        return true;
    }




    public void onInitialApplication(final AbstractCard card) {
        this.applyModify(card);
    }


    public void atEndOfTurn(final AbstractCard card, final CardGroup group){
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction(){
            public void update(){
                this.isDone=true;
                this.addToBot(new AbstractGameAction(){
                    public void update(){
                        this.isDone=true;
                        this.addToBot(new AbstractGameAction() {
                            public void update() {
                                this.isDone=true;
                                if(TlipocaModifier.this.extraModifyForThisTurn.contains(supportedModify.TWINCAST) && !TlipocaModifier.this.extraModify.contains(supportedModify.TWINCAST)){
                                    CardPatch.newVarField.twinCast.set(card,false);
                                    TlipocaModifier.this.extraModifyForThisTurn.remove(supportedModify.TWINCAST);
                                }

                                if(TlipocaModifier.this.extraModifyForThisTurn.contains(supportedModify.EPHEMERAL) && !TlipocaModifier.this.extraModify.contains(supportedModify.EPHEMERAL)){
                                    CardPatch.newVarField.ephemeral.set(card,false);
                                    TlipocaModifier.this.extraModifyForThisTurn.remove(supportedModify.EPHEMERAL);
                                }

                                if(TlipocaModifier.this.extraModifyForThisTurn.contains(supportedModify.ETERNITY) && !TlipocaModifier.this.extraModify.contains(supportedModify.ETERNITY)){
                                    CardPatch.newVarField.eternity.set(card,false);
                                    TlipocaModifier.this.extraModifyForThisTurn.remove(supportedModify.ETERNITY);
                                }

                                if(TlipocaModifier.this.extraModifyForThisTurn.contains(supportedModify.RESONATE) && !TlipocaModifier.this.extraModify.contains(supportedModify.RESONATE)){
                                    CardPatch.newVarField.resonate.set(card,false);
                                    TlipocaModifier.this.extraModifyForThisTurn.remove(supportedModify.RESONATE);
                                }
                                card.initializeDescription();
                            }
                        });
                    }
                });
            }
        }  );
    }






    public AbstractCardModifier makeCopy() {
        final TlipocaModifier copy = new TlipocaModifier();
        copy.extraModifyForThisTurn.addAll(this.extraModifyForThisTurn);
        copy.extraModify.addAll(this.extraModify);
        return copy;
    }

    public String identifier(final AbstractCard card) {
        return TlipocaModifier.ID;
    }


    static {
        TlipocaModifier.ID="TlipocaMod:TlipocaModifier";
    }



    public enum supportedModify{
        TWINCAST,
        RESONATE,
        ETERNITY,
        EPHEMERAL;

        private static /* synthetic */ supportedModify[] $values() {
            return new supportedModify[] { supportedModify.TWINCAST, supportedModify.ETERNITY, supportedModify.EPHEMERAL , supportedModify.RESONATE };
        }
    }
}

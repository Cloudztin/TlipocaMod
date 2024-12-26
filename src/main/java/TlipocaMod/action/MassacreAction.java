package TlipocaMod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class MassacreAction extends AbstractGameAction {

    private final AbstractCard card;
    private AbstractMonster tar;
    public List<AbstractMonster> hits;

    public MassacreAction(AbstractCard card, AbstractMonster tar, List<AbstractMonster> hits) {
        this.card = card;
        this.tar = tar;
        this.hits = hits;
    }

    public MassacreAction(AbstractCard card, List<AbstractMonster> hits) {
        this.card = card;
        this.tar = null;
        this.hits = hits;
    }


    public void update() {
        boolean repeat = false;
        if (this.tar == null)
            this.tar = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        if (this.tar.getIntentBaseDmg() >= 0 && !this.hits.contains(tar)) {
            repeat = true;
            this.hits.add(tar);
        }
        if (repeat)
            addToTop(new MassacreAction(this.card, this.hits));


        for (int i = 0; i < card.magicNumber; i++)
            addToTop(new DamageAction(this.tar, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AttackEffect.SLASH_HEAVY));

        this.isDone = true;
    }
}

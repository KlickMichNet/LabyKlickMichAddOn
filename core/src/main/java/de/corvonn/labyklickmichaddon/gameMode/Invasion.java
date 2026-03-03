package de.corvonn.labyklickmichaddon.gameMode;

import de.corvonn.labyklickmichaddon.objects.GameMode;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.serverapi.EconomyUpdateEvent;
import net.labymod.serverapi.core.model.display.EconomyDisplay;

public class Invasion extends AbstractGameMode {
    public Invasion() {
        super(GameMode.INVASION);
    }

    @Override
    protected void disable() {
        EconomyDisplay cash = new EconomyDisplay("cash", 0);
        cash.visible(false);
        Laby.fireEvent(new EconomyUpdateEvent(cash));
    }

    public enum LowHealthEffectStrength {
        DISABLED(0),
        REDUCED(0.3f),
        NORMAL(0.6f),
        INCREASED(1f);

        private final float strength;

        LowHealthEffectStrength(float strength) {
            this.strength = strength;
        }

        public float getStrength() {
            return strength;
        }
    }
}

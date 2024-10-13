package de.corvonn.labyklickmich.objects;

import de.corvonn.labyklickmich.gameMode.AbstractGameMode;
import de.corvonn.labyklickmich.gameMode.Todo;
import org.jetbrains.annotations.NotNull;

public enum GameMode {
    TODO("TODO", Todo.class),
    UNKNOWN("unknown", null);

    private final String name;
    private final Class<? extends AbstractGameMode> clazz;

    private GameMode(String name, Class<? extends AbstractGameMode> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getId() {
        return super.name();
    }

    public @NotNull String getDisplayName() {
        return name;
    }

    public Class<? extends AbstractGameMode> getClazz() {
        return clazz;
    }
}

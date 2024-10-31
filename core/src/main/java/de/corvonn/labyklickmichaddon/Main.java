package de.corvonn.labyklickmichaddon;

import de.corvonn.labyklickmichaddon.listener.EventHandler;
import de.corvonn.labyklickmichaddon.objects.widgets.hud.AddonHudWidgetRegistry;
import de.corvonn.labyklickmichaddon.utils.ServerClientConnector;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class Main extends LabyAddon<Configuration> {
    private static Main instance;
    private static GameModeRegistry gameModeRegistry;
    private static ConfigProperty<Boolean> configEnabled;
    private static ServerClientConnector serverClientConnector;
    private static AddonHudWidgetRegistry addonHudWidgetRegistry;

    @Override
    protected void enable() {
        this.registerSettingCategory();
        gameModeRegistry = new GameModeRegistry();
        instance = this;
        configEnabled = configuration().enabled();

        registerListener(new EventHandler());
        serverClientConnector = new ServerClientConnector();

        gameModeRegistry.requestCurrentGameMode();
        addonHudWidgetRegistry = new AddonHudWidgetRegistry();

        firstSetup();
    }

    private void firstSetup() {
        ConfigProperty<Integer> version = configuration().klickmichConfigVersion();
        if(version.get() > 0) return;

        version.set(1);
        addonHudWidgetRegistry.getLeaderBoardHudWidget().firstSetup();
    }


    @Override
    protected Class<Configuration> configurationClass() {
        return Configuration.class;
    }

    public static Main getInstance() {
        return instance;
    }

    public static GameModeRegistry getGameModeRegistry() {
        return gameModeRegistry;
    }

    public static boolean isEnabled() {
        return configEnabled.get();
    }

    public static boolean isDisabled() {
        return !isEnabled();
    }

    public static ServerClientConnector getServerClientConnector() {
        return serverClientConnector;
    }

    public static AddonHudWidgetRegistry getAddonHudWidgetRegistry() {
        return addonHudWidgetRegistry;
    }
}

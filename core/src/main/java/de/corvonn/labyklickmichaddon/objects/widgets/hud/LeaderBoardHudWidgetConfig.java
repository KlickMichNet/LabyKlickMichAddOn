package de.corvonn.labyklickmichaddon.objects.widgets.hud;

import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class LeaderBoardHudWidgetConfig extends TextHudWidgetConfig {

    @SliderSetting(min = 1, max = 12, steps = 1)
    private final ConfigProperty<Integer> displayedLines = new ConfigProperty<>(3);

    @SwitchSetting
    private final ConfigProperty<Boolean> lineBreakAfterKey = new ConfigProperty<>(true);

    public LeaderBoardHudWidgetConfig() {
        lineBreakAfterKey.visibilitySupplier(() -> {
            return super.formatting().getOrDefault() != Formatting.VALUE_ONLY;
        });
    }

    public ConfigProperty<Integer> getDisplayedLines() {
        return displayedLines;
    }

    public ConfigProperty<Boolean> getLineBreakAfterKey() {
        return lineBreakAfterKey;
    }

    public boolean getLineBreakAfterKeyValue() {
        return super.formatting().getOrDefault() != Formatting.VALUE_ONLY && lineBreakAfterKey.get();
    }
}

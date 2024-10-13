package de.corvonn.labyklickmich.objects.widgets.hud;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;

public class RankingHudWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine line;
    private boolean visible = false;

    public RankingHudWidget(HudWidgetCategory category) {
        super("ranking");

        bindCategory(category);
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);

        line = createLine(
            Component.translatable("labyklickmich.hudWidget.ranking.label"),
            Component.translatable("labyklickmich.hudWidget.data_unknown")
        );
    }


    public void setNewRank(int rank) {
        line.updateAndFlush(Component.text(rank));
    }

    public void visibility(boolean b) {
        visible = b;
    }

    @Override
    public boolean isVisibleInGame() {
        return visible;
    }
}

package de.corvonn.labyklickmichaddon.objects.widgets.hud;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.api.util.bounds.area.RectangleAreaPosition.PositionUnit;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoardHudWidget extends TextHudWidget<LeaderBoardHudWidgetConfig> {

    private boolean visible = false;
    private TextLine line;

    public LeaderBoardHudWidget(HudWidgetCategory category) {
        super("leaderboard", LeaderBoardHudWidgetConfig.class);

        bindCategory(category);
    }

    @Override
    public void load(LeaderBoardHudWidgetConfig config) {
        super.load(config);

        List<Component> exampleLines = new ArrayList<>();
        int maxLines = config.getDisplayedLines().get();
        for(int i = 1; i <= maxLines; i++)
            exampleLines.add(Component.translatable("labyklickmich.hudWidget.leaderboard.exampleLine").argument(Component.text(i)));

        line = createLine(
            Component.translatable("labyklickmich.hudWidget.leaderboard.label"),
            Component.empty()
        );

        updateLeaderBoard(exampleLines);
    }

    public void updateLeaderBoardWithServerAPIComponent(List<ServerAPIComponent> lb) {
        List<Component> components = new ArrayList<>();
        LabyModProtocolService service = Laby.references().labyModProtocolService();

        for(ServerAPIComponent c : lb) {
            components.add(service.mapComponent(c));
        }

        updateLeaderBoard(components);
    }

    private void updateLeaderBoard(List<Component> lb) {
        TextComponent.Builder leaderBoard = null;
        if(config.getLineBreakAfterKeyValue()) leaderBoard = Component.text();

        int maxLines = config.getDisplayedLines().get();

        int currentLines = 1;
        for(Component component : lb) {
            if(leaderBoard == null) leaderBoard = Component.text();
            else leaderBoard.append("\n");
            leaderBoard.append(component);

            if(currentLines++ >= maxLines) break;
        }

        if(leaderBoard == null) leaderBoard = Component.text();

        line.updateAndFlush(leaderBoard.build());
    }


    public void visibility(boolean b) {
        visible = b;
    }

    @Override
    public boolean isVisibleInGame() {
        return visible;
    }

    public void firstSetup() {
        config.useGlobal().set(false);
        config.formatting().set(Formatting.VALUE_ONLY);
        config.background().enabled().set(true);
        config.setEnabled(true);
        config.setAreaIdentifier(config.areaIdentifier().findBestPosition(PositionUnit.RIGHT));
    }
}

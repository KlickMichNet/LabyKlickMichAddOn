package de.corvonn.labyklickmichaddon.objects.widgets.hud;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;

public class AddonHudWidgetRegistry {
    private final RankingHudWidget rankingHudWidget;
    private final LeaderBoardHudWidget leaderBoardHudWidget;
    private final HudWidgetCategory widgetCategory;

    public AddonHudWidgetRegistry() {
        HudWidgetRegistry registry = Laby.labyAPI().hudWidgetRegistry();
        widgetCategory = new HudWidgetCategory("klickmichnet");
        registry.categoryRegistry().register(widgetCategory);

        this.rankingHudWidget = new RankingHudWidget(widgetCategory);
        this.leaderBoardHudWidget = new LeaderBoardHudWidget(widgetCategory);

        registry.register(rankingHudWidget);
        registry.register(leaderBoardHudWidget);
    }

    public RankingHudWidget getRankingHudWidget() {
        return rankingHudWidget;
    }

    public LeaderBoardHudWidget getLeaderBoardHudWidget() {
        return leaderBoardHudWidget;
    }
}

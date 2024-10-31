package de.corvonn.labyklickmichaddon.objects.widgets.wheel;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;

public class CommandWheelWidget extends WheelWidget {

    @Override
    public void initialize(Parent parent) {
        this.refresh();
        super.initialize(parent);
    }

    private void refresh() {
        for(int i = 0; i < 3; i++) {
            WheelWidget.Segment segment = new WheelWidget.Segment();
            addSegment(segment);
        }
    }
}

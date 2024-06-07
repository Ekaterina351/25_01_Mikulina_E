package footprints.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PassableCellWidget extends CellWidget {

    public PassableCellWidget() {
        super(Color.LIGHT_GRAY);
    }

    @Override
    public void addItem(CellItemWidget cellItemWidget) {
        super.addItem(cellItemWidget);

        if (cellItemWidget instanceof PlayerWidget playerWidget) {
            _cellWidgetColor = playerWidget.getPlayerColor();
        }
    }
}

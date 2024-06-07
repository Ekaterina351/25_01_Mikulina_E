package footprints.ui;

import javax.swing.*;
import java.awt.*;

public abstract class CellItemWidget {

    protected abstract Image getImage();

    protected abstract Dimension getSize();

    public void drawItem(Graphics g) {
        Dimension size = getSize();

        int x = (int) (CellWidget.CELL_SIZE / 2 - size.getWidth() / 2);
        int y = (int) (CellWidget.CELL_SIZE / 2 - size.getHeight() / 2);

        g.drawImage(getImage(), x, y, (int) size.getWidth(), (int) size.getHeight(), null);
    }
}

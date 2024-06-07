package footprints.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CellWidget extends JPanel {
    public static final int CELL_SIZE = 65;
    protected Color _cellWidgetColor;
    private final List<CellItemWidget> _items;

    public CellWidget(Color cellWidgetColor) {
        _cellWidgetColor = cellWidgetColor;
        _items = new ArrayList<>();
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        // Устанавливаем прозрачный фон
        setBackground(new Color(0, 0, 0, 0));
    }

    private void drawHexagon(Graphics2D g2d, Polygon hexagon) {
        g2d.setColor(_cellWidgetColor);
        g2d.fillPolygon(hexagon);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(hexagon);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Polygon hexagon = createHexagon();
        drawHexagon(g2d, hexagon);

        if (!_items.isEmpty()) {
            CellItemWidget item = _items.getFirst();
            item.drawItem(g2d);
        }
    }

    /**
     * Создает шестиугольник на основании знания о размере клетки, в которой он будет содержаться.
     * @return Шестиугольник
     */
    private Polygon createHexagon() {
        Polygon polygon = new Polygon();

        // Координаты шестиугольника определяется относительно его центра
        int centerX = CellWidget.CELL_SIZE / 2;
        int centerY = CellWidget.CELL_SIZE / 2;
        int sideSize = CellWidget.CELL_SIZE / 2;

        for (int i = 0; i < 6; i++) {
            int xval = (int) (centerX + sideSize
                    * Math.cos(i * 2 * Math.PI / 6D));
            int yval = (int) (centerY + sideSize
                    * Math.sin(i * 2 * Math.PI / 6D));
            polygon.addPoint(xval, yval);
        }

        return polygon;
    }

    public void addItem(CellItemWidget cellItemWidget) {
        if (_items.size() == 1) {
            throw new IllegalArgumentException();
        }

        _items.add(cellItemWidget);
    }

    public void removeItem(CellItemWidget cellItemWidget) {
        if (_items.isEmpty()) {
            throw new IllegalArgumentException();
        }

        _items.remove(cellItemWidget);
    }
}

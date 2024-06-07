package footprints.ui;

import footprints.Direction;
import footprints.Player;
import footprints.events.PlayerActionEvent;
import footprints.events.PlayerActionListener;
import footprints.field.AbstractCell;
import footprints.field.Field;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FieldWidget extends JPanel  {
    private static final int MARGIN = 10;
    private final Field _field;
    private final WidgetFactory _widgetFactory;
    private final List<AbstractCell> _seenCells;

    public FieldWidget(@NotNull Field field, @NotNull WidgetFactory widgetFactory) {
        _field = field;
        _widgetFactory = widgetFactory;
        _seenCells = new ArrayList<>();
        fillField();
        calculateSize();
        setSize(calculateSize());

        _field.getPlayer().addPlayerActionListener(new PlayerController());
    }

    /**
     * Вычисляет размеры поля. В силу того, что рисовка осуществляется по абсолютным координатам,
     * отсутствует возможность воспользоваться встроенным методом getSize() у JPanel для определения
     * размера поля. Поэтому размер определяется вручную следующим образом:
     * 1) Определяется максимальная координата X и максимальная координата Y у виджетов клеток (CellWidget)
     * 2) На основании найденных координат вычисляет размер поля (домноженном на специальный масштабирующий
     * коэффициент).
     */
    private Dimension calculateSize() {
        int maxX = -1, maxY = -1;

        for (Component c : getComponents()) {
            CellWidget child = (CellWidget) c;

            if (maxX == -1 && maxY == -1) {
                maxX = child.getX();
                maxY = child.getY();
                continue;
            }

            if (child.getX() > maxX) { maxX = child.getX(); }
            if (child.getY() > maxY) { maxY = child.getY(); }
        }

        // 1.3 - масштабирующий коэффициент для ширины
        int width = (int) (maxX + 1.3 * CellWidget.CELL_SIZE + MARGIN);
        // 1.5 - масштабирующий коэффициент для высоты
        int height = (int) (maxY + 1.5 * CellWidget.CELL_SIZE + MARGIN);

        return new Dimension(width, height);
    }

    /**
     * Отрисовывает поле. Рисует стартовую клетку с некоторым отсутоп от левого верхнего угла,
     * после чего отрисовывает всех соседей стартовой клетки.
     */
    private void fillField() {
        setLayout(null);
        AbstractCell startCell = _field.getStartCell();

        int x, y;
        x = y = MARGIN;

        CellWidget startCellWidget = _widgetFactory.create(startCell);
        // Устанавливаем положение стартовой клетки на экране
        startCellWidget.setBounds(x, y, CellWidget.CELL_SIZE, CellWidget.CELL_SIZE);
        add(startCellWidget);
        // Чтобы не рисовать те клетки, которые уже были отрисованы, вводим специальный
        // список seenCells, хранящий те клетки, которые уже были отрисованы. Данный список нужен,
        // т.к. отрисовка осуществляет рекурсивным методом, и нужно где-то хранить данные о том, какие клетки
        // уже были отрисованы. Возможно, стоит вынести эту переменную в отдельный параметр у нижнего fillField.
        _seenCells.add(startCell);

        for (var pair : startCell) {
            // Каждая следующая клетка рисуется относительно положения стартовой клетки!!!
            fillField(startCellWidget, pair.getKey(), pair.getValue());
        }

        // Очищаем список отрисованных клеток
        _seenCells.clear();
    }

    /**
     * Продолжение метода, отрисовывающего поле.
     * @param cellWidget Стартовый (опорный) виджет, относительного которого будет рисоваться следующий
     * @param direction Направление, в котором нужно отрисовать новый виджет
     * @param cell Новая клетка для отрисовки
     */
    private void fillField(CellWidget cellWidget, Direction direction, AbstractCell cell) {
        // Не рисуем ничего, если переданная клетка уже рисовалась
        if (_seenCells.contains(cell)) {
            return;
        }

        CellWidget nextCellWidget = _widgetFactory.create(cell);

        // Чтобы отрисовать новую шестиугольную клетку, нам необходимо определить отступ от стартовой клетки,
        // хранящейся в cellWidget. Для окончательно определения координаты необходимо прибавить смещение к
        // соответствующим координатам виджета, относительно которого мы рисуем (коэффициенты смещения считаются
        // относительно направления direction).
        double[] offset = calculateOffset(direction);
        int x = (int) Math.round(cellWidget.getX() + CellWidget.CELL_SIZE * offset[0]);
        int y = (int) Math.round(cellWidget.getY() + CellWidget.CELL_SIZE * offset[1]);
        // Однако, в процессе расчета может получиться так, что новые координаты x и y
        // будут отрицательными, чего быть не должно, т.к. начало координат - верхний левый угол (0, 0) и все
        // координаты положительны. Если так вышло, что получилось что-то отрицательное, нам необходимо сдвинуть всех
        // детей таким образом, чтобы отрицательные координаты стали нулевыми.
        if (x < 0 || y < 0) {
            int newX = x < 0 ? -x : 0;
            int newY = y < 0 ? -y : 0;

            for (Component c : getComponents()) {
                CellWidget child = (CellWidget) c;
                child.setLocation(child.getX() + newX, child.getY() + newY);
            }

            if (x < 0) { x = 0; }
            if (y < 0) { y = 0; }
        }

        nextCellWidget.setBounds(x, y, CellWidget.CELL_SIZE, CellWidget.CELL_SIZE);
        add(nextCellWidget);
        _seenCells.add(cell);

        for (var pair : cell) {
            fillField(nextCellWidget, pair.getKey(), pair.getValue());
        }
    }

    /**
     * Вычисление коэффициентов смещения для отрисовки в зависимости от направления.
     * Были получены в ходе выяснения математического вопроса о перемещении шестиугольников...
     * Первый коэффициент масштабирует x координату, второй - y координату.
     * @param direction Направление
     * @return Коэффициенты смещения
     */
    private double[] calculateOffset(Direction direction) {
        double[] offset = new double[0];
        switch (direction) {
            case NORTH -> {
                offset = new double[]{0, -0.8660254037844386};
            }
            case NORTHWEST -> {
                offset = new double[]{-0.75, -0.4330127018922193};
            }
            case SOUTHWEST -> {
                offset = new double[]{-0.75, 0.4330127018922193};
            }
            case SOUTH -> {
                offset = new double[]{0, 0.8660254037844386};
            }
            case NORTHEAST -> {
                offset = new double[]{0.75, -0.4330127018922193};
            }
            case SOUTHEAST -> {
                offset = new double[]{0.75, 0.4330127018922193};
            }
        }
        return offset;
    }

    private class PlayerController implements PlayerActionListener {

        @Override
        public void playerMoved(@NotNull PlayerActionEvent event) {
            PlayerWidget playerWidget = _widgetFactory.getWidget(event.getPlayer());
            CellWidget from = _widgetFactory.getWidget(event.getFromCell());
            CellWidget to = _widgetFactory.getWidget(event.getToCell());
            from.removeItem(playerWidget);
            to.addItem(playerWidget);
        }

        @Override
        public void playerGotKey(@NotNull PlayerActionEvent event) {
            Player player = event.getPlayer();
            CellWidget cellWidget = _widgetFactory.getWidget(player.getPosition());
            KeyWidget batteryWidget = _widgetFactory.getWidget(event.getKey());
            cellWidget.removeItem(batteryWidget);
            _widgetFactory.remove(event.getKey());
        }
    }
}

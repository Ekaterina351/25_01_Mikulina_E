package footprints.field;

import footprints.Direction;

public class ColumnField extends Field {
    // Поле состоит из 10 столбцов из шестиугольников, в каждом из столбцов по 10 шестиугольников
    private final static int _COLUMNS_COUNT = 6;
    private final static int _COLUMNS_HEIGHT = 6;
    private final static int _TARGET_COLUMN_NUM = 4;
    private final static int _TARGET_COLUMN_POS = 5;

    public int columnsCount() {
        return _COLUMNS_COUNT;
    }

    public int columnsHeight() {
        return _COLUMNS_HEIGHT;
    }

    @Override
    protected AbstractCell buildField() {
        // Метод написан таким образом, чтобы обрабатывать любые положения целевой позиции,
        // поэтому не стоит упрощать условие в тернарном операторе, т.к. координаты могут поменяться
        var startCell = (_TARGET_COLUMN_NUM == 0 && _TARGET_COLUMN_POS == 0)
                ? new TargetCell()
                : new PassableCell();

        AbstractCell colCell = startCell;
        for (int i = 0; i < _COLUMNS_COUNT; i++) {
            AbstractCell rowCell = colCell;
            for (int j = 1; j < _COLUMNS_HEIGHT; j++) {
                AbstractCell next = (i == _TARGET_COLUMN_NUM && j == _TARGET_COLUMN_POS)
                        ? new TargetCell()
                        : new PassableCell();
                rowCell.setNeighbor(next, Direction.SOUTH);
                rowCell = next;
            }

            if (i + 1 < _COLUMNS_COUNT) {
                AbstractCell nextCol = (_TARGET_COLUMN_NUM == i && _TARGET_COLUMN_POS == 0)
                        ? new TargetCell()
                        : new PassableCell();
                if (i % 2 == 0) {
                    colCell.setNeighbor(nextCol, Direction.SOUTHEAST);
                }
                else {
                    colCell.setNeighbor(nextCol, Direction.NORTHEAST);
                }
                colCell = nextCol;
            }
        }

        return startCell;
    }
}

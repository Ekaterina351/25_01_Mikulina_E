package footprints.field;

import footprints.items.Key;
import footprints.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public abstract class Field implements Iterable<AbstractCell> {
    protected AbstractCell _startCell;

    public Field() {
        _startCell = buildField();
    }

    protected abstract AbstractCell buildField();

    public AbstractCell getStartCell() {
        return _startCell;
    }

    public List<Key> getRemainingKeys() {
        List<Key> remainingKeys = new ArrayList<>();
        for (AbstractCell cell: this) {
            if (cell instanceof PassableCell passableCell && passableCell.hasKey()) {
                remainingKeys.add(passableCell.getKey());
            }
        }
        return remainingKeys;
    }

    public Player getPlayer() {
        for (AbstractCell cell: this) {
            if (cell.hasPlayer()) {
                return cell.getPlayer();
            }
        }
        return null;
    }

    public TargetCell getTargetCell() {
        for (AbstractCell cell: this) {
            if (cell instanceof TargetCell targetCell) {
                return targetCell;
            }
        }
        return null;
    }

    @Override
    public @NotNull Iterator<AbstractCell> iterator() {
        Set<AbstractCell> visited = new HashSet<>();

        List<AbstractCell> needToVisit = new ArrayList<>();
        needToVisit.add(_startCell);
        do {
            AbstractCell cell = needToVisit.removeLast();
            for (var entry: cell) {
                if (!needToVisit.contains(entry.getValue()) && !visited.contains(entry.getValue())) {
                    needToVisit.add(entry.getValue());
                }
            }
            visited.add(cell);
        } while (!needToVisit.isEmpty());

        return visited.iterator();
    }

    @Override
    public String toString() {
        int cellsCount = 0;
        int targetCellsCount = 0;
        for (var cell: this) {
            if (cell instanceof TargetCell) {
                targetCellsCount++;
            }
            cellsCount++;
        }
        return String.format("footprints.field.Field[CellsCount=%d, KeysCount=%d, TargetCellsCount=%d]",
                cellsCount,
                getRemainingKeys().size(),
                targetCellsCount);
    }
}

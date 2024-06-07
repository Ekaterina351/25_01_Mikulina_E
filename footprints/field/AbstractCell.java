package footprints.field;

import footprints.Direction;
import footprints.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AbstractCell implements Iterable<Map.Entry<Direction, AbstractCell>> {
    private final Map<Direction, AbstractCell> _neighbors;
    private Player _playerInside;

    public AbstractCell() {
        _neighbors = new HashMap<>();
        _playerInside = null;
    }

    public AbstractCell getNeighbor(@NotNull Direction direction) {
        return _neighbors.get(direction);
    }

    public void setNeighbor(@NotNull AbstractCell neighbor, @NotNull Direction direction) {
        // Если сосед в направлении уже имеется, или сосед есть у переданной клетки, выкинуть исключение
        if (getNeighbor(direction) != null && getNeighbor(direction) != neighbor ||
                neighbor.getNeighbor(direction.getOppositeDirection()) != null
                    && neighbor.getNeighbor(direction.getOppositeDirection()) != this) {
            throw new IllegalArgumentException();
        }

        _neighbors.put(direction, neighbor);
        if (neighbor.getNeighbor(direction.getOppositeDirection()) == null) {
            neighbor.setNeighbor(this, direction.getOppositeDirection());
        }

        Direction clockwiseNeighborDir = direction.nextDirectionClockwise();
        AbstractCell clockwiseNeighbor = getNeighbor(clockwiseNeighborDir);

        Direction anticlockwiseNeighborDir = direction.nextDirectionAnticlockwise();
        AbstractCell anticlockwiseNeighbor = getNeighbor(anticlockwiseNeighborDir);

        // Соединяем соседа по часовой стрелке с переданной ячейкой
        if (clockwiseNeighbor != null
                && clockwiseNeighbor.getNeighbor(anticlockwiseNeighborDir) != neighbor) {
            clockwiseNeighbor.setNeighbor(neighbor, anticlockwiseNeighborDir);
        }

        // Соединяем соседа против часовой стрелке с переданной ячейкой
        if (anticlockwiseNeighbor != null
                && anticlockwiseNeighbor.getNeighbor(clockwiseNeighborDir) != neighbor) {
            anticlockwiseNeighbor.setNeighbor(neighbor, clockwiseNeighborDir);
        }
    }

    public Direction isNeighbor(@NotNull AbstractCell cell) {
        for (var entry: _neighbors.entrySet()) {
            if (entry.getValue() == cell) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean hasPlayer() {
        return _playerInside != null;
    }

    public boolean setPlayer(Player newPlayer) {
        if (hasPlayer()) {
            return false;
        }

        if (newPlayer != null) {
            newPlayer.setPosition(this);
        }

        _playerInside = newPlayer;
        return true;
    }

    public Player extractPlayer() {
        if (!hasPlayer()) {
            return null;
        }

        _playerInside.setPosition(null);
        Player temp = _playerInside;
        _playerInside = null;
        return temp;
    }

    public Player getPlayer() {
        return _playerInside;
    }

    @Override
    public @NotNull Iterator<Map.Entry<Direction, AbstractCell>> iterator() {
        return _neighbors.entrySet().iterator();
    }
}

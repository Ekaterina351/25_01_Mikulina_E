package footprints.ui;

import footprints.Player;
import footprints.field.AbstractCell;
import footprints.field.PassableCell;
import footprints.field.TargetCell;
import footprints.field.UnpassableCell;
import footprints.items.BlueKey;
import footprints.items.Key;
import footprints.items.RedKey;
import footprints.items.YellowKey;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WidgetFactory {
    private final Map<AbstractCell, CellWidget> _cells = new HashMap<>();
    private final Map<Player, PlayerWidget> _player = new HashMap<>();
    private final Map<Key, KeyWidget> _keys = new HashMap<>();

    public CellWidget getWidget(@NotNull AbstractCell cell) {
        return _cells.get(cell);
    }

    public CellWidget create(@NotNull AbstractCell cell) {
        if (_cells.containsKey(cell)) {
            return _cells.get(cell);
        };

        CellWidget cellWidget;
        switch (cell) {
            case PassableCell entries -> cellWidget = new PassableCellWidget();
            case UnpassableCell entries -> cellWidget = new UnpassableCellWidget();
            case TargetCell entries -> cellWidget = new TargetCellWidget();
            default -> {
                throw new IllegalArgumentException();
            }
        }

        if (cell.hasPlayer()) {
            PlayerWidget playerWidget = create(cell.getPlayer());
            ((PassableCellWidget) cellWidget).addItem(playerWidget);
        }
        else if (cell instanceof PassableCell passableCell && passableCell.hasKey()) {
            KeyWidget keyWidget = create(passableCell.getKey());
            ((PassableCellWidget) cellWidget).addItem(keyWidget);
        }

        _cells.put(cell, cellWidget);
        return cellWidget;
    }

    public KeyWidget create(@NotNull Key key) {
        if (_keys.containsKey(key)) {
            return _keys.get(key);
        }

        KeyWidget keyWidget = switch (key) {
            case RedKey redKey -> new RedKeyWidget();
            case BlueKey blueKey -> new BlueKeyWidget();
            case YellowKey yellowKey -> new YellowKeyWidget();
            default -> throw new IllegalArgumentException();
        };
        _keys.put(key, keyWidget);
        return keyWidget;
    }

    public KeyWidget getWidget(@NotNull Key key) {
        return _keys.get(key);
    }

    public void remove(@NotNull Key key) {
        _keys.remove(key);
    }

    public PlayerWidget create(@NotNull Player player) {
        if (_player.containsKey(player)) {
            return _player.get(player);
        }

        PlayerWidget playerWidget = new PlayerWidget(Color.ORANGE);
        _player.put(player, playerWidget);
        return playerWidget;
    }

    public PlayerWidget getWidget(@NotNull Player player) {
        return _player.get(player);
    }
}

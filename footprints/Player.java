package footprints;

import footprints.events.PlayerActionEvent;
import footprints.events.PlayerActionListener;
import footprints.field.AbstractCell;
import footprints.field.PassableCell;
import footprints.items.Key;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private AbstractCell _position;
    private final List<Key> _foundKeys;
    private final List<PlayerActionListener> _listeners;

    public Player() {
        _position = null;
        _foundKeys = new ArrayList<>();
        _listeners = new ArrayList<>();
    }

    public boolean move(Direction direction) {
        AbstractCell neighbor = _position.getNeighbor(direction);
        if (neighbor == null) {
            return false;
        }

        AbstractCell oldPosition = _position;
        if (!neighbor.setPlayer(this)) {
            return false;
        }
        _position = neighbor;

        getKey();

        firePlayerMoved(oldPosition, _position);
        return true;
    }

    private void getKey() {
        if (_position instanceof PassableCell passableCell && passableCell.hasKey()) {
            Key key = passableCell.extractKey();
            _foundKeys.add(key);
            firePlayerGotKey(key);
        }
    }

    public List<Key> getFoundKeys() {
        return _foundKeys;
    }

    public AbstractCell getPosition() {
        return _position;
    }

    public void setPosition(AbstractCell newPosition) {
        if (newPosition != null && _position != null && newPosition != _position) {
            _position.extractPlayer();
        }
        _position = newPosition;
    }

    public void addPlayerActionListener(PlayerActionListener listener) {
        _listeners.add(listener);
    }

    public void removePlayerActionListener(PlayerActionListener listener) {
        _listeners.remove(listener);
    }

    private void firePlayerMoved(AbstractCell oldPosition, AbstractCell newPosition) {
        for (PlayerActionListener listener: _listeners) {
            PlayerActionEvent event = new PlayerActionEvent(listener);
            event.setPlayer(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.playerMoved(event);
        }
    }

    private void firePlayerGotKey(Key key) {
        for (PlayerActionListener listener: _listeners) {
            PlayerActionEvent event = new PlayerActionEvent(listener);
            event.setPlayer(this);
            event.setKey(key);
            listener.playerGotKey(event);
        }
    }
}

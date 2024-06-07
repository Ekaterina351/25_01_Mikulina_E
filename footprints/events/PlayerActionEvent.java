package footprints.events;

import footprints.field.AbstractCell;
import footprints.items.Key;
import footprints.Player;

import java.util.EventObject;

public class PlayerActionEvent extends EventObject {
    private Player _player;
    private AbstractCell _fromCell;
    private AbstractCell _toCell;
    private Key _key;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PlayerActionEvent(Object source) {
        super(source);
    }

    public void setPlayer(Player player) {
        _player = player;
    }

    public Player getPlayer() {
        return _player;
    }

    public void setFromCell(AbstractCell oldPosition) {
        _fromCell = oldPosition;
    }

    public AbstractCell getFromCell() {
        return _fromCell;
    }

    public void setToCell(AbstractCell newPosition) {
        _toCell = newPosition;
    }

    public AbstractCell getToCell() {
        return _toCell;
    }

    public void setKey(Key key) {
        _key = key;
    }

    public Key getKey() {
        return _key;
    }
}

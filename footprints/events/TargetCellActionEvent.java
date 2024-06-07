package footprints.events;

import footprints.Player;

import java.util.EventObject;

public class TargetCellActionEvent extends EventObject {
    private Player _player;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TargetCellActionEvent(Object source) {
        super(source);
    }

    public void setPlayer(Player player) {
        _player = player;
    }

    public Player getPlayer() {
        return _player;
    }
}

package footprints.events;

import footprints.GameState;
import org.jetbrains.annotations.NotNull;

import java.util.EventObject;

public class GameActionEvent extends EventObject  {
    private GameState _state;
    private String _userMessage;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameActionEvent(Object source) {
        super(source);
    }

    public GameState getState() {
        return _state;
    }

    public void setState(@NotNull GameState newState) {
        _state = newState;
    }

    public String getUserMessage() {
        return _userMessage;
    }

    public void setUserMessage(String userMessage) {
        _userMessage = userMessage;
    }
}

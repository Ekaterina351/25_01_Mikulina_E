package footprints.events;

import org.jetbrains.annotations.NotNull;

public interface GameActionListener {
    void gameStatusChanged(@NotNull GameActionEvent event);
}

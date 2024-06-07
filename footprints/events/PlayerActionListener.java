package footprints.events;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface PlayerActionListener extends EventListener {
    void playerMoved(@NotNull PlayerActionEvent event);

    void playerGotKey(@NotNull PlayerActionEvent event);
}

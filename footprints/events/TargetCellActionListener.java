package footprints.events;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface TargetCellActionListener extends EventListener {
    void playerAtTargetCell(@NotNull TargetCellActionEvent event);
}

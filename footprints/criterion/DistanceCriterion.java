package footprints.criterion;

import footprints.events.PlayerActionEvent;
import footprints.events.PlayerActionListener;
import org.jetbrains.annotations.NotNull;

/**
 * Абстрактный класс для критериев, связанных с пройденным расстоянием.
 */
public abstract class DistanceCriterion extends Criterion implements PlayerActionListener {

    private int _distanceTravelled = 0;

    public int distanceTravelled() {
        return _distanceTravelled;
    }

    @Override
    public void playerMoved(@NotNull PlayerActionEvent event) {
        _distanceTravelled += 1;
    }

    @Override
    public void playerGotKey(@NotNull PlayerActionEvent event) {
    }
}

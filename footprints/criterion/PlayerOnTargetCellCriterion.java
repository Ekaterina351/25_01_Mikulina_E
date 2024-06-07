package footprints.criterion;

import footprints.Player;
import footprints.field.TargetCell;

/**
 * Критерий, определяющий, что игрок находится на целевой позиции.
 */
public class PlayerOnTargetCellCriterion extends Criterion {
    private Player _player;

    public void setPlayer(Player player) {
        _player = player;
    }

    @Override
    public boolean isSatisfied() {
        return _player.getPosition() instanceof TargetCell;
    }

    @Override
    public String message() {
        return "игрок должен находится на целевой позиции";
    }
}

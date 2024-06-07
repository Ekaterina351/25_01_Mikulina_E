package footprints.criterion;

import footprints.Direction;
import footprints.Player;
import footprints.field.AbstractCell;
import footprints.field.PassableCell;
import footprints.field.TargetCell;

/**
 * Критерий, определяющий, может ли игрок двигаться.
 * Необходим для определения ситуации, когда игрок попадает в "клетку" из пройденных следов.
 */
public class CannotMoveAnywhereCriterion extends Criterion {
    private Player _player;

    public void setPlayer(Player player) {
        _player = player;
    }

    @Override
    public boolean isSatisfied() {
        AbstractCell playerPos = _player.getPosition();
        for (Direction dir : Direction.values()) {
            // Если вокруг клетки есть сосед, в которого можно перейти (нет следа),
            // или сосед это целевая клетка, значит ещё есть куда двигаться
            AbstractCell nextCell = playerPos.getNeighbor(dir);
            if ((nextCell instanceof PassableCell passableCell
                    && !passableCell.hasFootPrint()) || nextCell instanceof TargetCell) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String message() {
        return "игрок никуда не может сдвинуться";
    }
}

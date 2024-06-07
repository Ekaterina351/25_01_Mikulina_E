package footprints.criterion;

import footprints.Player;

/**
 * Критерий, определяющий, что определенное кол-во ключей было собрано.
 */
public class KeysCollectedCriterion extends Criterion {
    private Player _player;
    private int _keysCount;

    public void setPlayer(Player player) {
        _player = player;
    }

    public void setKeysCount(int keysCount) {
        _keysCount = keysCount;
    }

    @Override
    public boolean isSatisfied() {
        return _player.getFoundKeys().size() == _keysCount;
    }

    @Override
    public String message() {
        return "должны быть собраны (%d) ключи на поле".formatted(_keysCount);
    }
}

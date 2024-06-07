package footprints.criterion;

import footprints.Player;
import footprints.items.Key;

public class KeyMustBeTakenCriterion extends Criterion {

    private Key _key;
    private Player _player;

    public void setKey(Key key) {
        _key = key;
    }

    public void setPlayer(Player player) {
        _player = player;
    }

    @Override
    public boolean isSatisfied() {
        return _player.getFoundKeys().contains(_key);
    }

    @Override
    public String message() {
        return "должен быть взят " + _key.toString();
    }
}

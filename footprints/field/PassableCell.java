package footprints.field;

import footprints.items.Key;
import footprints.Player;

public class PassableCell extends AbstractCell {
    private Key _keyInside;
    private Player _playerWhoLeftFootPrint;

    public PassableCell() {
        _keyInside = null;
        _playerWhoLeftFootPrint = null;
    }

    @Override
    public boolean setPlayer(Player newPlayer) {
        if (hasFootPrint()) {
            return false;
        }

        if (!super.setPlayer(newPlayer)) {
            return false;
        }

        _playerWhoLeftFootPrint = newPlayer;
        return true;
    }

    public boolean setKey(Key key) {
        if (!hasPlayer() && key.setCell(this)) {
            _keyInside = key;
            return true;
        }

        return false;
    }

    public Key extractKey() {
        if (_keyInside == null) {
            return null;
        }

        _keyInside.setCell(null);
        Key temp = _keyInside;
        _keyInside = null;
        return temp;
    }

    public Key getKey() {
        return _keyInside;
    }

    public boolean hasKey() {
        return _keyInside != null;
    }

    public boolean hasFootPrint() {
        return _playerWhoLeftFootPrint != null;
    }

    public Player whoLeftFootPrint() {
        return _playerWhoLeftFootPrint;
    }
}

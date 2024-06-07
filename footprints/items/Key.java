package footprints.items;

import footprints.field.PassableCell;

public class Key {
    private PassableCell _storage;

    public Key() {
        _storage = null;
    }

    public boolean setCell(PassableCell passableCell) {
        if (_storage != null && _storage != passableCell && passableCell != null) {
            return false;
        }

        _storage = passableCell;
        return true;
    }

    public PassableCell getCell() {
        return _storage;
    }
}

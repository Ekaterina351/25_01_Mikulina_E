package footprints.seeder;

import footprints.Player;
import footprints.field.Field;
import footprints.items.Key;

import java.util.List;

public abstract class Seeder {
    protected Field _field;
    protected List<Key> _keys;
    protected Player _player;

    public Seeder(Field field) {
        _field = field;
        run();
    }

    public Field getField() {
        return _field;
    }

    public List<Key> getKeys() {
        return _keys;
    }

    public Player getPlayer() {
        return _player;
    }

    private void run() {
        seedPlayer(_field);
        seedKeys(_field);
    }

    abstract protected void seedPlayer(Field field);

    abstract protected void seedKeys(Field field);
}

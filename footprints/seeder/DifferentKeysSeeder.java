package footprints.seeder;

import footprints.Direction;
import footprints.Player;
import footprints.field.ColumnField;
import footprints.field.Field;
import footprints.field.PassableCell;
import footprints.items.BlueKey;
import footprints.items.Key;
import footprints.items.RedKey;
import footprints.items.YellowKey;

import java.util.ArrayList;
import java.util.List;

public class DifferentKeysSeeder extends Seeder {

    public DifferentKeysSeeder() {
        super(new ColumnField());
    }

    @Override
    protected void seedPlayer(Field field) {
        _player = new Player();
        field.getStartCell().setPlayer(_player);
    }

    @Override
    protected void seedKeys(Field field) {
        _keys = List.of(new RedKey(), new BlueKey(), new YellowKey());

        PassableCell cell1 = (PassableCell) field.getStartCell().getNeighbor(Direction.SOUTHEAST).getNeighbor(Direction.SOUTHEAST);
        cell1.setKey(_keys.getFirst());

        PassableCell cell2 = (PassableCell) field.getStartCell().getNeighbor(Direction.SOUTH).getNeighbor(Direction.SOUTHEAST);
        cell2.setKey(_keys.get(1));

        PassableCell cell3 = (PassableCell) cell2.getNeighbor(Direction.SOUTHEAST);
        cell3.setKey(_keys.getLast());
    }
}

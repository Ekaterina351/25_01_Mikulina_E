package footprints.seeder;

import footprints.Direction;
import footprints.Player;
import footprints.field.ColumnField;
import footprints.field.Field;
import footprints.field.PassableCell;
import footprints.items.Key;

public class SimpleSeeder extends Seeder {

    public SimpleSeeder() {
        super(new ColumnField());
    }

    @Override
    protected void seedPlayer(Field field) {
        field.getStartCell().setPlayer(new Player());
    }

    @Override
    protected void seedKeys(Field field) {
        PassableCell cell1 = (PassableCell) field.getStartCell().getNeighbor(Direction.SOUTHEAST).getNeighbor(Direction.SOUTHEAST);
        cell1.setKey(new Key());

        PassableCell cell2 = (PassableCell) field.getStartCell().getNeighbor(Direction.SOUTH).getNeighbor(Direction.SOUTHEAST);
        cell2.setKey(new Key());
    }
}

package footprints.criterion;

import footprints.Player;
import footprints.field.Field;
import footprints.items.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * Критерий, требущий чтобы на поле остались определенные ключи.
 */
public class SpecifiedKeysMustStayCriterion extends Criterion {
    private Field _field;
    private List<Key> _keys;
    private Player _player;
    private Criterion _criterion;

    public void setField(Field field) { _field = field; }

    public void setKeys(List<Key> keys) { _keys = List.copyOf(keys); }

    public void setKeys(Key... keys) { setKeys(List.of(keys)); }

    public void setPlayer(Player player) { _player = player; }

    @Override
    public boolean isSatisfied() {
        if (_criterion == null) {
            List<Criterion> criterionList = new ArrayList<>();
            for (Key key : _keys) {
                KeyMustBeTakenCriterion criterion = new KeyMustBeTakenCriterion();
                criterion.setKey(key);
                criterion.setPlayer(_player);
                criterionList.add(new NotCriterion(criterion));
            }

            _criterion = new AndCriterion(criterionList);
        }

        return _criterion.isSatisfied();

        /*
        // Если кол-во ключей на поле и кол-во требуемых ключей не совпадает,
        // значит что критерий не удовлетворен
        List<Key> remainingKeys = _field.getRemainingKeys();
        if (_keys.size() != remainingKeys.size()) {
            return false;
        }

        // Проверяем, что на поле остались только требуемые ключи
        for (Key key : _keys) {
            if (!remainingKeys.contains(key)) {
                return false;
            }
        }

        // Оставшиеся на поле ключи и требуемые ключи совпали
        return true;
        */
    }

    @Override
    public String message() {
        StringBuilder builder = new StringBuilder();
        builder.append("на поле должны остаться ключи: ");

        for (Key key : _keys) {
            builder.append(key.toString()).append(", ");
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}

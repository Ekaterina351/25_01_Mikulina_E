package footprints.criterion;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Логическое И для критериев.
 * Чтобы быть соблюденным, требует соблюдения всех переданных критериев.
 */
public class AndCriterion extends Criterion {
    private final List<Criterion> _criterions;

    public AndCriterion(List<Criterion> criterions) {
        _criterions = List.copyOf(criterions);
    }

    public AndCriterion(Criterion ...criterions) {
        this(List.of(criterions));
    }

    @Override
    public boolean isSatisfied() {
        for (Criterion criterion : _criterions) {
            if (!criterion.isSatisfied()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String message() {
        return _criterions.stream().map(Criterion::message).collect(Collectors.joining(" и "));
    }
}

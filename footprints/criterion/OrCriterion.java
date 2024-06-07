package footprints.criterion;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Логическое ИЛИ для критериев.
 * Чтобы быть соблюденным, требует соблюдения хотя бы одного из переданных критериев.
 */
public class OrCriterion extends Criterion {
    private final List<Criterion> _criterions;

    public OrCriterion(List<Criterion> criterions) {
        _criterions = List.copyOf(criterions);
    }

    public OrCriterion(Criterion ...criterions) {
        this(List.of(criterions));
    }

    @Override
    public boolean isSatisfied() {
        for (Criterion criterion : _criterions) {
            if (criterion.isSatisfied()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String message() {
        return _criterions.stream().map(Criterion::message).collect(Collectors.joining(" или "));
    }
}

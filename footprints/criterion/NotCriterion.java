package footprints.criterion;

/**
 * Логическое НЕ для критериев.
 * Чтобы быть соблюденным, требует отрицания другого критерия.
 */
public class NotCriterion extends Criterion {
    private final Criterion _criterion;

    public NotCriterion(Criterion criterion) {
        _criterion = criterion;
    }

    @Override
    public boolean isSatisfied() {
        return !_criterion.isSatisfied();
    }

    @Override
    public String message() {
        return "не %s".formatted(_criterion.message());
    }
}

package footprints.criterion;

public class DistanceLessThanCriterion extends DistanceCriterion {

    private final int _threshold;

    public DistanceLessThanCriterion(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException();
        }

        _threshold = threshold;
    }

    @Override
    public boolean isSatisfied() {
        return distanceTravelled() < _threshold;
    }

    @Override
    public String message() {
        return "пройденное расстояние должно быть меньше чем %d".formatted(_threshold);
    }
}

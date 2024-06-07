package footprints.criterion;

public class DistanceGreaterThanCriterion extends DistanceCriterion {

    private final int _threshold;

    public DistanceGreaterThanCriterion(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException();
        }

        _threshold = threshold;
    }

    @Override
    public boolean isSatisfied() {
        return distanceTravelled() > _threshold;
    }

    @Override
    public String message() {
        return "пройденное расстояние должно быть больше чем %d".formatted(_threshold);
    }
}

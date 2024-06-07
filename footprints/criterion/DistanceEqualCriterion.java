package footprints.criterion;

public class DistanceEqualCriterion extends DistanceCriterion {

    private final int _threshold;

    public DistanceEqualCriterion(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException();
        }

        _threshold = threshold;
    }

    @Override
    public boolean isSatisfied() {
        return distanceTravelled() == _threshold;
    }

    @Override
    public String message() {
        return "пройденное расстояние должно быть точно равно %d".formatted(_threshold);
    }
}

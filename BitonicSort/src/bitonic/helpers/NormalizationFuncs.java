package bitonic.helpers;

public class NormalizationFuncs {
    public NormalizationFuncs() {
    }

    public static double mapToRange(double value, double start, double end) {
        return (value - start) / (end - start);
    }

    public static double remapToRange(double value, double startOld, double endOld, double startNew, double endNew) {
        return (value - startOld) / (endOld - startOld) * (endNew - startNew) + startNew;
    }
}

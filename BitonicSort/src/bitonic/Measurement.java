package bitonic;

public class Measurement {
    private double time;
    private int    size;

    public Measurement(double time, int size) {
        this.time = time;
        this.size = size;
    }

    public double getTime() {
        return this.time;
    }

    public int getSize() {
        return this.size;
    }
}

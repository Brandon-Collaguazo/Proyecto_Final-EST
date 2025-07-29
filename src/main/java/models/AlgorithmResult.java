package models;

public class AlgorithmResult {
    private String nameAlgorithm;;
    private int steps;
    private long timeMs;

    public AlgorithmResult(String nameAlgorithm, int steps, long timeMs) {
        this.nameAlgorithm = nameAlgorithm;
        this.steps = steps;
        this.timeMs = timeMs;
    }

    public String getNameAlgorithm() {
        return nameAlgorithm;
    }

    public void setNameAlgorithm(String nameAlgorithm) {
        this.nameAlgorithm = nameAlgorithm;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(long timeMs) {
        this.timeMs = timeMs;
    }

    @Override
    public String toString() {
        return nameAlgorithm + ";" + steps + ";" + timeMs;
    }
}

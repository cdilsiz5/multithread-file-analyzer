package src.util;

public class Timer {
    private final long startTime;

    public Timer() {
        this.startTime = System.nanoTime();
    }

    public void printElapsed(String label) {
        long endTime = System.nanoTime();
        long durationNano = endTime - startTime;
        long durationMillis = durationNano / 1_000_000;
        double durationSeconds = durationNano / 1_000_000_000.0;

        System.out.printf("%s süresi: %d ns (%d ms, %.3f s)%n",
                label, durationNano, durationMillis, durationSeconds);
    }
}

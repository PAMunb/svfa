package samples;

public class SameFlowSample {

    public static void main(String args[]) {
        int a, b;

        a = source();

        if (a > 100) {
            b = a + 1;
        } else {
            b = a - 1;
        }

        sink(b);
    }

    private static int source() {
        return 1;
    }

    private static void sink(int v) {
        System.out.println(v);
    }
}

package samples;

public class StringBuilderSample {

    public static void main(String args[]) {
        String s = source();
        StringBuilder buffer = new StringBuilder();
        buffer.append(s);
        sink(buffer.toString());
    }

    private static String source() {
        return "secret";
    }

    private static void sink(String s) {

    }
}

package samples;

public class StringBuilderComplexSample {

    public static void main(String args[]) {
        String s = source();
        String name = "pepito";
        StringBuilder buffer = new StringBuilder();
        buffer.append("|").append(name).append("|").append(s);
        sink(buffer.toString());
    }

    private static String source() {
        return "secret";
    }

    private static void sink(String s) {

    }
}

package samples.conflicts;

public class Sample01 {
    public Integer x, y, z;

    public void execute(){
        x = 0 + 1;
        y = 5;
        z = 0 + x;
    }

    public static void main(String[] args) {
        Sample01 s = new Sample01();
        s.execute();
    }
}

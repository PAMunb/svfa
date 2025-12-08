package samples.conflicts;

public class Sample03 {
    public int x, y, z;

    public void execute(){
        x = 0 + 1;
        y = 5;
        z = 0 + x;
    }
}

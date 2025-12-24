package samples.conflicts;

public class Sample02 {

    public void execute(){
        int x, y, z;
        x = 0 + 1;
        y = 5;
        z = 0 + x;
        System.out.println(z);
    }

}

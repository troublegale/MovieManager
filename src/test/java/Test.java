import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String a =  "asda sdads ad ad a dsad asd a";
        String[] w = a.split("\\s+", 2);
        System.out.println(w.length);
        System.out.println(w[0]);
        System.out.println(w[1]);
    }
}

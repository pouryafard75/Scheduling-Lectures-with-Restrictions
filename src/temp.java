import java.util.ArrayList;

public class temp {
    public static void main(String[] args) {
        t2 test = new t2();
        System.out.println(test.getTps().size());
        test.getTps().add(new example(1,4));
        System.out.println(test.getTps().size());
        example qq = new example(1,4);
        System.out.println(qq.getTT_ii());
        qq.getTT_ii().replace("Q","R");
        System.out.println(qq.getTT_ii());

    }

}
class t2{
    private ArrayList<example> tps;
    t2()
    {
        tps = new ArrayList<>();
        tps.add(new example(1,1));
        tps.add(new example(1,3));
    }

    public ArrayList<example> getTps() {
        return tps;
    }
}
class example{

    private int day;
    private int part;
    private String TT_ii;

    example(int day, int part)
    {
        this.day = day;
        this.part = part;
        TT_ii = "QWERT";
    }

    public int getDay() {
        return day;
    }

    public String getTT_ii() {
        return TT_ii;
    }
}
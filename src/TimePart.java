import java.util.ArrayList;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class TimePart {
    public int day;
    public int part;
    ArrayList<Lecture> lectures = new ArrayList<>();
    static ArrayList<TimePart> list = new ArrayList<>();

    public TimePart(int day, int part) {
        this.day = day;
        this.part = part;
        lectures = new ArrayList<>();
    }

    public static TimePart get(int day,int part){
        for(TimePart walk:list)
            if(walk.day == day && walk.part == part)
                return walk;
        TimePart ret = new TimePart(day,part);
        list.add(ret);
        return ret;
    }

    public static int lastDay(){
        int max = 0;
        for(TimePart walk:list){
            if(walk.day>max)
                max = walk.day;
        }
        return max;
    }

    public String toString(){
        String ret = "day "+day+" part "+part+"\n";
        for(Lecture walk:lectures){
            ret+= walk.toString()+", ";
        }
        return ret;
    }

    public static String getAllToString(){
        String ret ="";
        for(TimePart walk:list){
            ret += walk.toString() +"\n";
        }
        return ret;
    }

}

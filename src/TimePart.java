import java.util.ArrayList;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class TimePart {

    private static final int PARTS_PER_DAY = 4;
    private int day;
    private int part;
    public ArrayList<Lecture> lectures;
    static ArrayList<TimePart> list = new ArrayList<>();

    public void addLecture(Lecture l)
    {
        lectures.add(l);
    }
    public ArrayList<Lecture> getLectures() {
        return new ArrayList<>(lectures);
    }

    //Getter for private fields
    public int getDay()
    {
        return day;
    }
    public int getPart()
    {
        return part;
    }
    // If I want to add a getter for arraylist, the caller still can change the array, (its not immutable like int and String)
    // Unless I clone a copy, so left it  for later commits. Moreover, I have to modify add functions too.

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
    public String info()
    {
        return  "Day " + day + ", part "+part ;
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
    static TimePart next(TimePart input)
    {
        if (input.getPart() >= PARTS_PER_DAY) return new TimePart(input.getDay() + 1,1);
        return new TimePart(input.getDay(),input.getPart()+1);
    }
}

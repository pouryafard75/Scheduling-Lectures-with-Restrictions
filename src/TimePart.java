import java.util.ArrayList;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class TimePart {

    public static final int PARTS_PER_DAY = 4;
    private int day;
    private int part;
    private ArrayList<Lecture> lectures;
    void addLecture(Lecture l)
    {
        lectures.add(l);
    }
    ArrayList<Lecture> getLectures() {
        return new ArrayList<>(lectures);
    }
    int getDay()
    {
        return day;
    }
    int getPart()
    {
        return part;
    }

    TimePart(int day, int part) {
        this.day = day;
        this.part = part;
        lectures = new ArrayList<>();
    }
    String info()
    {
        return  "Day " + day + ", part "+part ;
    }

    static TimePart next(TimePart input)
    {
        if (input == null)
            return new TimePart(1,1);
        if (input.getPart() >= PARTS_PER_DAY) return new TimePart(input.getDay() + 1,1);
        return new TimePart(input.getDay(),input.getPart()+1);
    }
}

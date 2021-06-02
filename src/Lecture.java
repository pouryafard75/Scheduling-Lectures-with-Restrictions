import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Lecture {
    public static ArrayList<Lecture> list =new ArrayList<>();

    private Set<Subject> subjects;
    private final Teacher supervisor;
    private ArrayList<Teacher> referees;
    private String name;

    Lecture(String name, Teacher supervisor) {
        this.name = name;
        this.supervisor = supervisor;
        referees = new ArrayList<>();
        subjects = new HashSet<>();
    }
    public Set<Subject> getSubjects() {
        return new HashSet<>(subjects);
    }
    public void addSubject(Subject s)
    {
        subjects.add(s);
    }
    public Teacher getSupervisor() {
        return supervisor;
    }
    public ArrayList<Teacher> getReferees() {
        return new ArrayList<>(referees);
    }
    public int getRefereesSize(){
        return referees.size();
    }
    public boolean isAssigned()
    {
        if (referees.size() == 2)
            return true;
        return false;
    }
    public int addReferee(Teacher t)
    {
        if (referees.size() == 2)
            return 0;
        referees.add(t);
        return 1;
    }
    public void clearReferees()
    {
        referees.clear();
    }

    //No usage, however adding a getter
    public String getName() {
            return name;
    }


    public static Lecture findByName(String name){
        for(Lecture walk:list)
            if(walk.name.equals(name))
                return walk;
        return null;
    }
    public static ArrayList<Lecture> findByParticipator(Teacher teacher){
        ArrayList<Lecture> ret = new ArrayList<>();
        for (Lecture walk:list){
            if(walk.supervisor.equals(teacher))
                ret.add(walk);
            for(Teacher ref:walk.referees)
                if(ref.equals(teacher))
                    ret.add(walk);
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getName());
        result.append(" | Supervisor : ");
        result.append(getSupervisor().getName());
        result.append(" | Referees : " );
        for (Teacher t : getReferees()) {
            result.append(t.getName()).append(" , ");
        }
        return result.toString();
    }

    public TimePart getTimePart(){
        for(TimePart timePart:TimePart.list){
            if(timePart.lectures.contains(this))
                return timePart;
        }
        return null;
    }

}

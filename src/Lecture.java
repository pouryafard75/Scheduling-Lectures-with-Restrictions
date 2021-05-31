import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Lecture {
    public static ArrayList<Lecture> list =new ArrayList<>();
    public Set<Subject> subjects;
    public Teacher supervisor;
    public ArrayList<Teacher> referees;

    private String name;

    //No usage, however adding a getter
    public String getName() {
            return name;
    }

    public Lecture(String name, Teacher supervisor) {
        this.name = name;
        this.supervisor = supervisor;
        referees = new ArrayList<>();
        subjects = new HashSet<>();
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
        String ret = name+": "+supervisor.getName()+"\n";
        for (Subject walk:subjects)
            ret+=walk.getName()+" ";
        return ret;
    }
    public String print(){
        String ret = name +" | "
                +"Supervisor: "+supervisor.getName()+" | ";
        ret += "referee(s): ";
        for (Teacher t:referees)
            ret+=t.getName()+", ";
        ret += " | ";
        ret += "Subject(s): ";
        for (Subject t:subjects)
            ret+=t.getName()+", ";
        ret += " | ";
        return ret;
    }
    public String output()
    {
        String ret = "";
        if (referees.size() == 2) {
            ret += name;
            ret += " ";
            ret += referees.get(0).getName();
            ret += " ";
            ret += referees.get(1).getName();
        }
        else {
            System.out.println("Refress Size of " + name + " are not 2 , they are " + referees.size());
            new Scanner(System.in).nextInt();
        }
        return ret;
    }

    public TimePart getTimePart(){
        for(TimePart timePart:TimePart.list){
            if(timePart.lectures.contains(this))
                return timePart;
        }
        return null;
    }

}

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Subject {
    public static ArrayList<Subject> list =new ArrayList<>();
    String name;
    Set<Teacher> teachers;

    public Subject(String name) {
        this.name = name;
        teachers = new HashSet<>();
    }

    public static Subject findByName(String name){
        for(Subject walk:list)
            if(walk.name.equals(name))
                return walk;
        return null;
    }

    @Override
    public String toString() {
        String ret = name+": ";
        for (Teacher walk:teachers)
            ret+=walk.name+" ";
        return ret;
    }
}

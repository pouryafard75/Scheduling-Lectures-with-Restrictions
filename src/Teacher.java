import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Teacher {
    static ArrayList<Teacher> list = new ArrayList<>();
    String name;
    Set<Subject> subjects;

    public Teacher(String name) {
        this.name = name;
        subjects = new HashSet<>();
    }

    static Teacher findByName(String str){
        for(Teacher walk:list)
            if(walk.name.equals(str))
                return walk;
        return null;
    }

    @Override
    public String toString() {
        String ret = name+": ";
        for (Subject walk:subjects)
            ret+=walk.name+", ";
        return ret;
    }
    public static ArrayList<Teacher> BySubject(Subject inp)
    {
        ArrayList<Teacher> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).subjects.contains(inp))
                result.add(list.get(i));
        }
        return result;

    }
    public boolean equals(Object o){
        return name == ((Teacher)o).name;
    }
}

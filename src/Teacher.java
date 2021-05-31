import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Teacher {
    static ArrayList<Teacher> list = new ArrayList<>();
    public Set<Subject> subjects;

    private String name;
    //Getter for private field
    public String getName()
    {
        return name;
    }

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
        for (Subject s:subjects)
            ret+=s.getName()+", ";
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

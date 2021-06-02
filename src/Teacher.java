import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Teacher {
    static ArrayList<Teacher> list = new ArrayList<>();
    private Set<Subject> subjects;
    private String name;

    Teacher(String name) {
        this.name = name;
        subjects = new HashSet<>();
    }
    public boolean isExpertInSubject(Subject s)
    {
        if (subjects.contains(s))
            return true;
        return false;
    }
    //Getter for private field
    String getName()
    {
        return name;
    }
    public Set<Subject> getSubjects() {
        return new HashSet<>(subjects);
    }
    public int getSubjectsSize()
    {
        return subjects.size();
    }
    void addSubject(Subject s)
    {
        subjects.add(s);
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Subject {
    public static ArrayList<Subject> list =new ArrayList<>();
    private Set<Teacher> teachers;
    private String name;
    public Subject(String name) {
        this.name = name;
        teachers = new HashSet<>();
    }
    //Getter for private field;
    String getName()
    {
        return name;
    }
    public Set<Teacher> getTeachers() {
        return new HashSet<>(teachers);
    }
    void addTeacher(Teacher t1)
    {
        teachers.add(t1);
    }
    public int getTeachersSize(){
        return teachers.size();
    }
    static Subject findByName(String name){
        for(Subject walk:list)
            if(walk.name.equals(name))
                return walk;
        return null;
    }

    @Override
    public String toString() {
        String ret = name+": ";
        for (Teacher t:teachers)
            ret+=t.getName()+" ";
        return ret;
    }
}

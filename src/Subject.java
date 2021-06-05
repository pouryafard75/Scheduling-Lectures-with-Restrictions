import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Subject {
    private Set<Teacher> teachers;
    private String name;
    public Subject(String name) {
        this.name = name;
        teachers = new HashSet<>();
    }
    String getName()
    {
        return name;
    }
    void addTeacher(Teacher teacher)
    {
        teachers.add(teacher);
    }
}

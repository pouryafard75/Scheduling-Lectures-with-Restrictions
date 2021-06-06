import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Subject {
    private Set<Teacher> teachers;
    private String name;
    public Subject(String name) {
        this.name = name;
        teachers = new LinkedHashSet<>();
    }
    String getName()
    {
        return name;
    }
    void addTeacher(Teacher teacher)
    {
        teachers.add(teacher);
    }
    public int getTeachersSize()
    {
        return teachers.size();
    }
    Set<Teacher> getTeachers()
    {
        return teachers;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Subject: ").append(name);
        stringBuilder.append(" | Prof(s): ");
        Iterator<Teacher> iterator = teachers.iterator();
        while(iterator.hasNext()) {
            Teacher teacher = iterator.next();
            stringBuilder.append(teacher.getName());
            if (iterator.hasNext()) stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
    static String fileFormatSubjectList(Set<Subject> subjects)
    {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Subject> iterator = subjects.iterator();
        stringBuilder.append(subjects.size()).append("\n");
        while(iterator.hasNext())
        {
            Subject subject = iterator.next();
            stringBuilder.append(subject.getName());
            if (iterator.hasNext()) stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

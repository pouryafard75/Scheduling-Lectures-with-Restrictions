import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
class Teacher {
    private Set<Subject> subjects;
    private String name;

    Teacher(String name) {
        this.name = name;
        subjects = new HashSet<>();
    }
    boolean isExpertInSubject(Subject s)
    {
        return subjects.contains(s);
    }
    String getName()
    {
        return name;
    }
    Set<Subject> getSubjects() {
        return new HashSet<>(subjects);
    }
    int getSubjectsSize()
    {
        return subjects.size();
    }
    void addSubject(Subject s)
    {
        subjects.add(s);
    }
}

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Lecture {
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
    Set<Subject> getSubjects() {
        return new HashSet<>(subjects);
    }
    void addSubject(Subject s)
    {
        subjects.add(s);
    }
    Teacher getSupervisor() {
        return supervisor;
    }
    ArrayList<Teacher> getReferees() {
        return new ArrayList<>(referees);
    }
    boolean isAssigned()
    {
        return referees.size() == 2;
    }
    void addReferee(Teacher t)
    {
        if (referees.size() == 2)
            return;
        referees.add(t);
    }
    public String getName() {
            return name;
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
}

import java.util.HashSet;
import java.util.Iterator;
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
    void addSubject(Subject subject)
    {
        subjects.add(subject);
    }

    String fileformat()
    {
        return name + "\n" +
                Subject.fileFormatSubjectList(subjects);
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Teacher: "). append(name);
        stringBuilder.append(" | Subject(s): ");
        Iterator<Subject> iterator = subjects.iterator();
        while(iterator.hasNext())
        {
            Subject subject = iterator.next();
            stringBuilder.append(subject.getName());
            if (iterator.hasNext()) stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}

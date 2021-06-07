import java.io.*;
import java.util.*;

class InputGenerator {

    private ArrayList<Lecture> lectures;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;
    private static final int SUBJECT_MAX_STRING_LENGTH = 5;
    private static final int TEACHER_MAX_STRING_LENGTH = 5;
    private static final int LECTURE_MAX_STRING_LENGTH = 5;
    private static final int PROF_PER_SUBJECT = 3;
    private static final int TEACHER_MINIMUM_SUBJECT = 2;
    private static final int SUBJ_PER_LECTURE = 2;
    private static final String LECTURES_FILE = "Lectures.txt";
    private static final String SUBJECTS_FILE = "Subjects.txt";
    private static final String TEACHERS_FILE = "Teachers.txt";
    private int subcount, teacherscount, leccounts;
    InputGenerator(int subcount , int teacherscount , int leccounts )
    {
        this.subcount = subcount;
        this.teacherscount = teacherscount;
        this.leccounts = leccounts;
        lectures = new ArrayList<>();
        teachers = new ArrayList<>();
        subjects = new ArrayList<>();
    }
    ScheduleInput populate()
    {
        populateSubjects();
        populateTeachers();
        populateLectures();
        return new ScheduleInput(lectures,teachers,subjects);
    }
    private void populateSubjects()
    {
        while(subjects.size() < subcount)
        {
            subjects.add(new Subject(randomString(SUBJECT_MAX_STRING_LENGTH)));
        }
    }

    private void populateTeachers()
    {
        while(teachers.size() < teacherscount)
            teachers.add(new Teacher(randomString(TEACHER_MAX_STRING_LENGTH)));
        for (Subject subject : subjects) {
            Set<Teacher> experts = ExpertTeachersOnSubject(subject);
            List<Teacher> nonexperts = new ArrayList<>(teachers);
            while (experts.size() < PROF_PER_SUBJECT)
            {
                Teacher selected = selectRandom(nonexperts);
                selected.addSubject(subject);
                nonexperts.remove(selected);
                experts.add(selected);
                subject.addTeacher((selected));
            }
        }
        for (Teacher teacher : teachers) {
            while (teacher.getSubjectsSize() < TEACHER_MINIMUM_SUBJECT) {
                List<Subject> subjectList = new ArrayList<>(subjects);
                subjectList.removeAll(teacher.getSubjects());
                Subject selected = selectRandom(subjectList);
                teacher.addSubject(selected);
                selected.addTeacher(teacher);
            }
        }
    }
    private void populateLectures() {
        while (lectures.size() < leccounts) {
            Subject sub = selectRandom(subjects);
            Teacher supervisor = selectRandom(new ArrayList<>(sub.getTeachers()));
            Lecture lecture = new Lecture(randomString(LECTURE_MAX_STRING_LENGTH),supervisor);
            lecture.addSubject(sub);
            List<Subject> validsubjects = new ArrayList<>(subjects);
            validsubjects.remove(sub);
            while (lecture.getSubjects().size() < SUBJ_PER_LECTURE)
            {
                Subject selected = selectRandom(validsubjects);
                lecture.addSubject(selected);
                validsubjects.remove(selected);
            }
            lectures.add(lecture);
        }
    }
    private <T> T selectRandom(List<T> list)
    {
        Random rnd = new Random();
        int index= (int)(rnd.nextFloat() * list.size());
        return list.get(index);
    }
    private Set<Teacher> ExpertTeachersOnSubject(Subject subject){
        Set<Teacher> experts= new LinkedHashSet<>();
        for (Teacher teacher : teachers) {
            if (teacher.isExpertInSubject(subject)) experts.add(teacher);
        }
        return experts;
    }
    private String randomString(int maxlength)
    {
        Random rnd = new Random();
        int desired_length = ((int) (rnd.nextFloat() * maxlength)) + 1;// Since we have at least string with length 1
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        while (result.length() < desired_length) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            result.append(CHARS.charAt(index));
        }
        return result.toString();
    }
    public void writeToFile() throws FileNotFoundException {
        writeSubjects();
        writeTeachers();
        writeLectures();

    }
    private void writeSubjects() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(SUBJECTS_FILE))));
        for (Subject subject: subjects) {
            out.println(subject.getName());
        }
        out.flush();
    }
    private void writeTeachers() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(TEACHERS_FILE))));
        for (Teacher teacher: teachers) {
            out.println(teacher.fileformat());
        }
        out.flush();
    }
    private void writeLectures() throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(LECTURES_FILE))));
        for (Lecture lecture: lectures) {
            out.println(lecture.fileformat());
        }
        out.flush();
    }

}

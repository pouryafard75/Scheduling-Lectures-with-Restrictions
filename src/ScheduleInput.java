import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScheduleInput {
    private ArrayList<Lecture> lectures;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;

    ScheduleInput(String teachers_filename , String subjects_filename, String lectures_filename)
    {
        lectures = new ArrayList<>();
        teachers = new ArrayList<>();
        subjects = new ArrayList<>();
        try {
            InitAllFromFiles(teachers_filename,subjects_filename,lectures_filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void InitAllFromFiles(String teachers_filename , String subjects_filename, String lectures_filename) throws Exception {
        initSubjectsFromFile(subjects_filename);
        initTeachersFromFile(teachers_filename);
        initLecturesFromFile(lectures_filename);

    }
    private void initSubjectsFromFile(String filename) throws FileNotFoundException {
        Scanner subjectInput = new Scanner(new File(filename));
        while (subjectInput.hasNext())
            subjects.add(new Subject(subjectInput.next()));
        subjectInput.close();
    }
    private void initTeachersFromFile(String filename) throws Exception {
        Scanner teacherInput = new Scanner(new File(filename));
        while (teacherInput.hasNext())
        {
            Teacher teacher = new Teacher(teacherInput.next());
            int subjectCount = teacherInput.nextInt();
            for (int i = 0; i < subjectCount; i++) {
                String subjectname = teacherInput.next();
                Subject subject = findSubjectByName(subjectname);
                if (subject == null)
                    throw new Exception("No Subject found with the name : " + subjectname);
                teacher.addSubject(subject);
                subject.addTeacher(teacher);
            }
            teachers.add(teacher);
        }
        teacherInput.close();
    }
    private void initLecturesFromFile(String filename) throws Exception {
        Scanner lecInput = new Scanner(new File(filename));
        while (lecInput.hasNext()){
            String lecname = lecInput.next();
            String teachername = lecInput.next();
            Teacher supervisor = findTeacherByName(teachername);
            if (supervisor == null)
                throw new Exception("No Teacher found with the name : " + teachername);
            Lecture lec = new Lecture(lecname,supervisor);
            int lectureCount = lecInput.nextInt();
            for (int i = 0; i < lectureCount; i++) {
                String subjectname = lecInput.next();
                Subject sub = findSubjectByName(subjectname);
                if (sub == null)
                    throw new Exception("No Subject found with the name : " + subjectname);
                lec.addSubject(sub);
            }
            lectures.add(lec);
        }
        lecInput.close();
    }

    //Find Methods
    private Subject findSubjectByName(String name) {
        for(Subject sub:subjects)
            if(sub.getName().equals(name))
                return sub;
        return null;
    }
    private Teacher findTeacherByName(String name){
        for(Teacher teacher:teachers)
            if(teacher.getName().equals(name))
                return teacher;
        return null;
    }

    ArrayList<Lecture> getLectures() {
        return new ArrayList<>(lectures);
    }
    ArrayList<Teacher> getTeachers() {
        return new ArrayList<>(teachers);
    }
    ArrayList<Subject> getSubjects() {
        return new ArrayList<>(subjects);
    }



}

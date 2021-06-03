import java.io.*;
import java.util.*;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Schedule {
    private ArrayList<Lecture> lectures;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;
    private ArrayList<TimePart> parts;
    private ScheduleOutput scheduleOutput;
    Schedule() {
        lectures = new ArrayList<>();
        teachers = new ArrayList<>();
        subjects = new ArrayList<>();
        parts = new ArrayList<>();
    }

    //Init methods
    void initializer(){
        try {
            initSubjects();
            initTeachers();
            initLectures();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Problem with initializer");
        }
    }
    private void initSubjects() throws FileNotFoundException {
        Scanner subjectInput = new Scanner(new File("Subjects.txt"));
        while (subjectInput.hasNext())
            subjects.add(new Subject(subjectInput.next()));
        subjectInput.close();
    }
    private void initTeachers() throws Exception {
        Scanner teacherInput = new Scanner(new File("Teachers.txt"));
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
    private void initLectures() throws Exception {
        Scanner lecInput = new Scanner(new File("Lectures.txt"));
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
    private TimePart getLastTimePart() { return parts.get(parts.size() - 1);  }

    //Print methods
    private void printTeachers()
    {
        for (Teacher t1: teachers) System.out.println(t1.getName());
    }
    private void printLectures() {
        for (Lecture l:  lectures)
            System.out.println(l);
    }

    private ArrayList<Lecture> unAssignedLectures() {
        ArrayList<Lecture> unassigned = new ArrayList<>();
        for(Lecture lec:lectures)
            if (!lec.isAssigned()) unassigned.add(lec);
        return unassigned;
    }
    private Lecture FindPossibleMatching(ArrayList<Lecture> notAssigned, PriorityQueue<Teacher> possibleTeachers) {

        for (Lecture lecture: notAssigned) {
            Set<Teacher> experts = new HashSet<>();
            for (Subject sub : lecture.getSubjects())
                experts.addAll(findExperts(sub, possibleTeachers));
            experts.remove(lecture.getSupervisor());
            if (experts.size() >= 2) {
                Iterator<Teacher> it = experts.iterator();
                lecture.addReferee(it.next());
                lecture.addReferee(it.next());
                return lecture;
            }
        }
        return null;
    }
    private Set<Teacher> findExperts(Subject sub, PriorityQueue<Teacher> possibleTeachers) {
        Set<Teacher> result = new HashSet<>();
        for (Teacher t: possibleTeachers)
            if (t.isExpertInSubject(sub)) result.add(t);
        return result;
    }
    private Set<Teacher> calculateBusyTeachers() {
        TimePart last = getLastTimePart();

        Set<Teacher> busyTeachers = new HashSet<>();
        ArrayList<Lecture> curLectures = last.getLectures();
        for (Lecture lec: curLectures) {
                busyTeachers.add(lec.getSupervisor());
                busyTeachers.addAll(lec.getReferees());
        }
        return busyTeachers;
    }
    private PriorityQueue<Teacher> calculateFreeTeachers() {
        PriorityQueue<Teacher> freeTeachers = new PriorityQueue<>(Comparator.comparingInt(Teacher::getSubjectsSize));
        Set<Teacher> busyTeachers = calculateBusyTeachers();
        for (Teacher t : teachers) {
            if (!busyTeachers.contains(t)) freeTeachers.add(t);
        }
        return freeTeachers;
    }
    ScheduleOutput solve() {
        int _day = 1;
        int _part = 1;
        TimePart curPart = new TimePart(_day,_part);
        parts.add(curPart);
        while(true)
        {
            ArrayList<Lecture> notAssigned = unAssignedLectures();
            if (notAssigned.isEmpty()) {
                System.out.println("Problem Solved");
                break;
            }
            PriorityQueue<Teacher> FreeTeachers = calculateFreeTeachers();
            Lecture lect = FindPossibleMatching(notAssigned,FreeTeachers);
            if (lect == null)
                {
                    curPart = TimePart.next(curPart);
                    parts.add(curPart);
                }
            else
            {
                getLastTimePart().addLecture(lect);
            }
        }
        return new ScheduleOutput(parts);
    }

}

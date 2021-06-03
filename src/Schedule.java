import java.util.*;
/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Schedule {
    private ArrayList<Lecture> lectures;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;
    private ArrayList<TimePart> parts;
    Schedule(ScheduleInput scheduleInput) {
        lectures = scheduleInput.getLectures();
        teachers = scheduleInput.getTeachers();
        subjects = scheduleInput.getSubjects();
        parts = new ArrayList<>();
    }
    private TimePart getLastTimePart() { return parts.get(parts.size() - 1);  }
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
    ScheduleOutput GreedySolve() {
        int _day = 1;
        int _part = 1;
        parts.add(new TimePart(_day,_part));
        while(true)
        {
            ArrayList<Lecture> notAssigned = unAssignedLectures();
            if (notAssigned.isEmpty()) {
                System.out.println("Problem Solved");
                break;
            }
            Lecture lect = FindPossibleMatching(notAssigned,calculateFreeTeachers());
            if (lect == null)
                parts.add(TimePart.next(getLastTimePart()));
            else
                getLastTimePart().addLecture(lect);
        }
        return new ScheduleOutput(parts);
    }
}

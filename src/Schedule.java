import java.util.*;


public abstract class Schedule {

    private ArrayList<Lecture> lectures;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;
    private ArrayList<TimePart> parts;

    Schedule(ScheduleInput scheduleInput)
    {
        setInputs(scheduleInput);
        parts = new ArrayList<>();
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    ArrayList<TimePart> getParts() {
        return parts;
    }
    TimePart addNewPart()
    {
        TimePart tp = TimePart.next(getLastTimePart());
        parts.add(tp);
        return tp;
    }
    TimePart getLastTimePart() {
        if (parts.size() == 0)
            return null;
        return parts.get(parts.size() - 1);
    }
    void setInputs(ScheduleInput scheduleInput)
    {
        lectures = scheduleInput.getLectures();
        teachers = scheduleInput.getTeachers();
        subjects = scheduleInput.getSubjects();
    }
    ArrayList<Lecture> unAssignedLectures() {
        ArrayList<Lecture> unassigned = new ArrayList<>();
        for(Lecture lec:lectures)
            if (!lec.isAssigned()) unassigned.add(lec);
        return unassigned;
    }
    Set<Teacher> findExperts(Subject sub, PriorityQueue<Teacher> possibleTeachers) {
        Set<Teacher> result = new HashSet<>();
        for (Teacher t: possibleTeachers)
            if (t.isExpertInSubject(sub)) result.add(t);
        return result;
    }
    Set<Teacher> calculateCurrentBusyTeachers() {
        TimePart last = getLastTimePart();
        Set<Teacher> busyTeachers = new HashSet<>();
        ArrayList<Lecture> curLectures = last.getLectures();
        for (Lecture lec: curLectures) {
            busyTeachers.add(lec.getSupervisor());
            busyTeachers.addAll(lec.getReferees());
        }
        return busyTeachers;
    }
    Set<Teacher> calculateCurrentFreeTeachers() {
        Set<Teacher> freeTeachers = new HashSet<>();
        Set<Teacher> busyTeachers = calculateCurrentBusyTeachers();
        for (Teacher teacher : teachers) {
            if (!busyTeachers.contains(teacher)) freeTeachers.add(teacher);
        }
        return freeTeachers;
    }
    abstract ScheduleOutput solve();
    int resultFitness()
    {
        return TimePart.numberOfParts(getLastTimePart());
    }
}

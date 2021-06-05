import java.sql.Time;
import java.util.*;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
class GreedyAlgorithm extends Schedule {
    GreedyAlgorithm(ScheduleInput scheduleInput) {
        super(scheduleInput);
    }
    private Lecture FindPossibleMatching(ArrayList<Lecture> notAssigned, PriorityQueue<Teacher> possibleTeachers) {

        for (Lecture lecture: notAssigned) {
            if (!possibleTeachers.contains(lecture.getSupervisor())) continue;
            Set<Teacher> experts = new HashSet<>();
            for (Subject subject : lecture.getSubjects())
                experts.addAll(findExperts(subject, new HashSet<>(possibleTeachers)));
            experts.remove(lecture.getSupervisor());
            if (experts.size() >= 2) {
                Iterator<Teacher> iterator = experts.iterator();
                lecture.addReferee(iterator.next());
                lecture.addReferee(iterator.next());
                return lecture;
            }
        }
        return null;
    }

    ScheduleOutput solve() {
        addNewPart();
        while(true)
        {
            ArrayList<Lecture> notAssigned = unAssignedLectures();
            if (notAssigned.isEmpty()) {
                System.out.println("Problem Solved");
                break;
            }
            PriorityQueue<Teacher> teachers_queue = new PriorityQueue<>(Comparator.comparingInt(Teacher::getSubjectsSize));
            teachers_queue.addAll(calculateCurrentFreeTeachers());
            Lecture lect = FindPossibleMatching(notAssigned,teachers_queue);
            if (lect == null)
                addNewPart();
            else
                getLastTimePart().addLecture(lect);
        }
        return new ScheduleOutput(getParts());
    }
}

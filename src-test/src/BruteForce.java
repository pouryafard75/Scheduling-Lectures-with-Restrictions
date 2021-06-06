import java.lang.reflect.Array;
import java.sql.Time;
import java.util.*;

class BruteForce extends Schedule {

    // I have only wrote this class to check the new design
    // There was no intention to write the brute-force code
    // Since its memory and time complexity is too high
    // And this project aims to solve the NP-Hard problem as simple as possible
    ArrayList<ArrayList<TimePart>> states = new ArrayList<>();


    BruteForce(ScheduleInput scheduleInput) {
        super(scheduleInput);
    }

    @Override
    ScheduleOutput solve() {

            addNewPart();
            states.add(getParts());
            ArrayList<TimePart> base;
            for (int bi = 0; bi < states.size(); bi++)
            {
                System.out.println("ooo");
                base = states.get(bi);
                states.remove(base);
                bi--;
                for (Lecture base_lecture : unAssignedLectures()) {
                    base_lecture.clearReferees();
                    ArrayList<Teacher> AllChoices = new ArrayList<>(possibleTeachersForALecture(base_lecture));
                    AllChoices.remove(base_lecture.getSupervisor());
                    AllChoices.sort(Comparator.comparing(Teacher::getName));
                    if (AllChoices.size() < 2)
                    {
                        continue;
                    }
                    for (int i = 0; i < AllChoices.size(); i++) {
                        for (int j = i + 1; j < AllChoices.size(); j++) {
                            Lecture lecture = cloneLecture(base_lecture);
                            Teacher ref1 = AllChoices.get(i);
                            Teacher ref2 = AllChoices.get(j);
                            lecture.addReferee(ref1);
                            lecture.addReferee(ref2);
                            ArrayList<TimePart> newparts = cloneTimePartArray(base);
                            newparts.get(newparts.size() - 1).addLecture(lecture);
                            states.add(newparts);
                        }
                    }
                }
                printAllStates();
            }

        return null;
    }

    Set<Teacher> possibleTeachersForALecture(Lecture lecture)
    {
        Set<Teacher> experts = new HashSet<>();
        for (Subject subject : lecture.getSubjects())
            experts.addAll(findExperts(subject, calculateCurrentFreeTeachers()));
        return experts;
    }

    Lecture cloneLecture(Lecture lecture)
    {
        Lecture result = new Lecture(lecture.getName(),lecture.getSupervisor());
        for (Teacher ref : lecture.getReferees())
            result.addReferee(ref);
        for (Subject sub : lecture.getSubjects())
            result.addSubject(sub);
        return result;
    }
    TimePart cloneTimePart(TimePart part)
    {
        TimePart result = new TimePart(part.getDay(), part.getPart());
        for (Lecture lecture : part.getLectures())
            result.addLecture(cloneLecture(lecture));
        return result;
    }
    ArrayList<TimePart> cloneTimePartArray(ArrayList<TimePart> parts)
    {
        ArrayList<TimePart> result = new ArrayList<>();
        for (TimePart tp: parts)
            result.add(cloneTimePart(tp));
        return result;
    }
    private void printAllStates()
    {
        for (ArrayList<TimePart> tparr : states)
        {
            for (TimePart timePart : tparr)
            {
                System.out.print(timePart.getDay() + "," + timePart.getPart() + ":");
                for (Lecture lecture : timePart.getLectures()) {
                    System.out.print(lecture.getName() + "|" + lecture.getReferees().get(0).getName() + "," + lecture.getReferees().get(1).getName() + "&");
                }
            }
            System.out.println();
        }

    }
}



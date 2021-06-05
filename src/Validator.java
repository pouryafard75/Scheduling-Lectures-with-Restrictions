import java.util.ArrayList;

public class Validator {
    ArrayList<TimePart> tps;
    Validator(ScheduleOutput result){
        tps = result.getParts();
    }
    boolean checkValidation()
    {
        return checkSimultaneity() && checkExpertise();
    }
    private boolean checkSimultaneity()
    {
        for (TimePart part : tps)
        {
            ArrayList<Teacher> busyNow = new ArrayList<>();
            for (Lecture lecture : part.getLectures())
            {
                if (busyNow.contains(lecture.getSupervisor()))
                {
                    System.out.println(lecture.getSupervisor().getName() + " is at two lectures at the same time in timepart: " + part.getDay() + "," + part.getPart());
                    return false;
                }
                busyNow.add(lecture.getSupervisor());
                for (Teacher ref : lecture.getReferees())
                {
                    if (busyNow.contains(ref)) {
                        System.out.println(ref.getName() + " is at two lectures at the same time in timepart" + part.getDay() + "," + part.getPart());
                        return false;
                    }
                    busyNow.add(ref);
                }
            }
        }
        return true;
    }
    private boolean checkExpertise()
    {
        for (TimePart part : tps)
        {
            for (Lecture lecture : part.getLectures())
            {
                for (Teacher ref : lecture.getReferees())
                {
                    boolean isExpert = false;
                    for (Subject subject: lecture.getSubjects())
                        if (ref.isExpertInSubject(subject))
                        {
                            isExpert = true;
                            break;
                        }
                    if (!isExpert) {
                        System.out.println(ref.getName() + " is not an expert for lecture " + lecture.getName());
                        return false;
                    }
                }
            }
        }
        return true;
    }
}


/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Main {
    public static void main(String[] args) {
        String TEACHERS_FILE_ADDR = "Teachers.txt";
        String SUBJECTS_FILE_ADDR = "Subjects.txt";
        String LECTURES_FILE_ADDR = "Lectures.txt";
        ScheduleInput scheduleInput = new ScheduleInput(TEACHERS_FILE_ADDR,SUBJECTS_FILE_ADDR,LECTURES_FILE_ADDR);
        Schedule schedule = new GreedyAlgorithm(scheduleInput);
        ScheduleOutput output = schedule.solve();
        Validator validator = new Validator(output);
        System.out.println(validator.checkValidation());
        output.printResult();
        output.writeToFile("Output.txt");
        output.exportExcel("Schedule.xls");
    }
}


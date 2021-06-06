import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Random;


public class TestDrive {
    @Test
    public static void main(String[] args) {

        Random rnd = new Random();
        int testNumbers = 10;
        int curTest = 0;
        while(curTest < testNumbers)
        {
            //Validation test
            int sub_count = (int) (rnd.nextFloat() * 10) + 5;
            int teach_count = sub_count + (int) (rnd.nextFloat() * 10) + sub_count;
            int lect_count = sub_count + teach_count + (int) (rnd.nextFloat() * 10) + 10;
            TestUnit test = new TestUnit(sub_count,teach_count ,  lect_count);
            Schedule schedule = new GreedyAlgorithm(test.populate());
            Validator validator = new Validator(schedule.solve());
            System.out.println("Validtor : " + validator.checkValidation());
            Assert.assertTrue(validator.checkValidation());
            curTest ++;
        }
        // Accuracy test
        String TEACHERS_FILE_ADDR = "Teachers-AccuracyTest.txt";
        String SUBJECTS_FILE_ADDR = "Subjects-AccuracyTest.txt";
        String LECTURES_FILE_ADDR = "Lectures-AccuracyTest.txt";
        final int ExpectedResultCost = 2 * TimePart.PARTS_PER_DAY;
        ScheduleInput accuracy_input = new ScheduleInput(TEACHERS_FILE_ADDR,SUBJECTS_FILE_ADDR,LECTURES_FILE_ADDR);
        Schedule schedule = new GreedyAlgorithm(accuracy_input);
        ScheduleOutput accuracy_output = schedule.solve();
        Assert.assertTrue(new Validator(accuracy_output).checkValidation());
        Assert.assertTrue(schedule.resultFitness() <= ExpectedResultCost);
        System.out.println("Cost of the algorithm : " + schedule.resultFitness()  + " <= " + ExpectedResultCost +  " (Expected)");

    }
}
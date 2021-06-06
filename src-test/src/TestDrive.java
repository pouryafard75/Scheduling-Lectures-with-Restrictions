import org.junit.Assert;
import org.junit.Test;
import java.util.Random;


public class TestDrive {
    @Test
    public static void main(String[] args) {

        Random rnd = new Random();
        int testNumbers = 10;
        int curTest = 0;
        while(curTest < testNumbers)
        {
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
    }
}
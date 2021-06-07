import junit.framework.*;
import org.junit.Test;

public class JUnitProgram extends TestCase {
    private Schedule schedule1;
    private int maxExpectedResult;
    protected void setUp(){
        String prefix = "Given-";
        String TEACHERS_FILE_ADDR = "Teachers.txt";
        String SUBJECTS_FILE_ADDR = "Subjects.txt";
        String LECTURES_FILE_ADDR = "Lectures.txt";
        ScheduleInput GivenInput = new ScheduleInput(prefix + TEACHERS_FILE_ADDR
                ,prefix + SUBJECTS_FILE_ADDR,prefix + LECTURES_FILE_ADDR);
        schedule1 = new GreedyAlgorithm(GivenInput);
        maxExpectedResult = 2 * TimePart.PARTS_PER_DAY;
    }

    @Override
    protected void tearDown() {

    }

    @Test
    public void test()
    {
        schedule1.solve();
        assertTrue(schedule1.resultFitness() < maxExpectedResult);
    }
}
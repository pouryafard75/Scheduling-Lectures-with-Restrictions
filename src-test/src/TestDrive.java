import org.junit.Test;

import java.io.*;

public class TestDrive {

    @Test
    public static void main(String[] args) {

        TestUnit test = new TestUnit(5,10, 10);
        ScheduleInput scheduleInput = test.populate();
        try {
            test.writeToFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scheduleInput.printAllInfo();


    }
}
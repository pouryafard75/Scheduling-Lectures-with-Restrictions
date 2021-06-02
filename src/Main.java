import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.initializer();
        schedule.solve();
        schedule.printResult();
        schedule.exportExcel();
    }
}



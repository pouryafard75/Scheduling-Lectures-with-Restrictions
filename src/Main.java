
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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


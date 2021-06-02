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


        int attempts = 1000;
        String bestResult;
        int bestDay;
        ArrayList<State> states = new ArrayList<>();
        State st1 = new State();
        bestResult = st1.result;
        bestDay = st1.get_num_of_dates();
        states.add(st1);
        for (int i = 0; i < attempts ; i++) {
            State st = new State();
            if (st.get_num_of_dates() < bestDay )
            {
                states.clear();
                states.add(st);
                bestDay = st.get_num_of_dates();
                bestResult = st.result;
            }
            else if (st.get_num_of_dates() ==  bestDay) {
                states.add(st);
            }
        }
        try
        {
            FileWriter myWriter = new FileWriter("Sample Output.txt");
            myWriter.write(bestResult);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(bestResult);
        schedule.exportExcel();
    }

}



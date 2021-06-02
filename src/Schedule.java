import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Schedule {

    public void exportExcel(){
        try {
            initializeFromFile();
            makeExcel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Sample Output.txt"));
        while (scanner.hasNext()){
            Lecture lec = Lecture.findByName(scanner.next());
            Teacher referee1 = Teacher.findByName(scanner.next());
            Teacher referee2 = Teacher.findByName(scanner.next());
            int day = scanner.nextInt();
            int part = scanner.nextInt();

            lec.clearReferees();
            lec.addReferee(referee1);
            lec.addReferee(referee2);
            TimePart timePart = TimePart.get(day,part);
            timePart.lectures.add(lec);
        }
        System.out.println("Successful initialize!");
    }

    private void makeExcel() throws IOException {
        String exportAdr = "Schedule.xls";
        HSSFWorkbook workbook = new HSSFWorkbook();

        //////////////////////////////////Days Sheet////////////////////////////////////
        HSSFSheet daysSheet = workbook.createSheet("Days");
        for(int dayCounter =1; dayCounter<= TimePart.lastDay();dayCounter++){
            for(int partCounter =1 ; partCounter<=4;partCounter++){
                int rowNumber = (dayCounter-1)*4 +(partCounter-1);
                HSSFRow currentRow = daysSheet.createRow(rowNumber);
                currentRow.createCell(0).setCellValue("day"+dayCounter+ " part"+partCounter);
                int lectureCounter =1;
                TimePart currentTimePart = TimePart.get(dayCounter,partCounter);
                for(Lecture walk:currentTimePart.lectures){
                    currentRow.createCell(lectureCounter).setCellValue(walk.print());
                    lectureCounter++;
                }
            }
        }
        for(int i=0;i<10;i++){
            daysSheet.autoSizeColumn(i);
        }

        //////////////////////////////////Teachers Sheet////////////////////////////////////
        HSSFSheet teachersSheet = workbook.createSheet("Teachers");
        HSSFRow firstRow = teachersSheet.createRow(0);
        firstRow.createCell(0).setCellValue("Teacher");
        for(int dayCounter=1;dayCounter<=TimePart.lastDay();dayCounter++){
            for (int partCounter = 1; partCounter <= 4; partCounter++) {
                int cellNumber = (dayCounter-1)*4 +partCounter;
                firstRow.createCell(cellNumber).setCellValue("day"+dayCounter+" part"+partCounter);
            }
        }
        int rowCounter=1;
        for(Teacher walk:Teacher.list){
            HSSFRow currentRow = teachersSheet.createRow(rowCounter);
            currentRow.createCell(0).setCellValue(walk.toString());
            ArrayList<HSSFCell> cells=new ArrayList<>();
            for(int dayCounter=1;dayCounter<=TimePart.lastDay();dayCounter++){
                for (int partCounter = 1; partCounter <= 4; partCounter++) {
                    int cellNumber = (dayCounter-1)*4 +partCounter;
                    cells.add(currentRow.createCell(cellNumber));
                }
            }
            for(Lecture lecture:Lecture.findByParticipator(walk)){
                TimePart timePart = lecture.getTimePart();
                int cellNumber = (timePart.getDay()-1)*4 + timePart.getPart();
                HSSFCell cell =  cells.get(cellNumber-1);
                String lastContent = cell.getStringCellValue();
                cell.setCellValue(lastContent + lecture.print());
            }
            rowCounter++;
        }
        for(int i=0;i<10;i++){
            teachersSheet.autoSizeColumn(i);
        }

        //////////////////////////////////Write to file////////////////////////////////////
        FileOutputStream outputFile = new FileOutputStream(exportAdr);
        workbook.write(outputFile);
        outputFile.close();
        System.out.println("Excel successfully generated!");
    }
}

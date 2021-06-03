import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import java.io.*;
import java.util.ArrayList;
class ScheduleOutput {
    private ArrayList<TimePart> parts;
    ScheduleOutput(ArrayList<TimePart> tp)
    {
        this.parts = tp;
    }
    private String resultInfo() {
        StringBuilder result = new StringBuilder();
        for ( TimePart tp: parts) {
            result.append(tp.info()).append(": ").append("\n");
            for (Lecture l : tp.getLectures()) result.append(l.toString()).append("\n");
        }
        return result.toString();
    }
    void printResult()
    {
        System.out.println(resultInfo());
    }
    void writeToFile(String file_addr)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(file_addr))));
            out.print(resultInfo());
            out.flush();
            System.out.println("File generated successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    void exportExcel(String file_addr)
    {
        try {
            makeExcel(file_addr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void makeExcel(String file_addr) throws IOException {
        //TODO : use correct outputs;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet daysSheet = workbook.createSheet("Lectures Schedule");

        HSSFRow row = daysSheet.createRow(0);
        for (int curPart = 1; curPart <= TimePart.PARTS_PER_DAY; curPart++)
            row.createCell(curPart).setCellValue("Part " + curPart);
        for (int curday = 1; curday <= parts.get(parts.size() - 1).getDay(); curday++) {
            row = daysSheet.createRow(curday);
            row.createCell(0).setCellValue("Day" + curday);
            for (int curPart = 1; curPart <= TimePart.PARTS_PER_DAY; curPart++) {
                StringBuilder stringBuilder = new StringBuilder();
                for (TimePart tp : parts) {
                    if (tp.getDay() == curday && tp.getPart() == curPart) {
                        for (Lecture lecture : tp.getLectures())
                            stringBuilder.append(lecture.toString()).append("\n\n");
                    }
                }
                HSSFCell cell = row.createCell(curPart);
                cell.setCellValue(stringBuilder.toString());
                CellStyle style = workbook.createCellStyle();
                style.setWrapText(true);
                cell.setCellStyle(style);

            }
        }
        for (int curPart = 1; curPart <= TimePart.PARTS_PER_DAY; curPart++)
            daysSheet.autoSizeColumn(curPart);
        FileOutputStream outputFile = new FileOutputStream(file_addr);
        workbook.write(outputFile);
        outputFile.close();
        System.out.println("Excel successfully generated!");
    }
}

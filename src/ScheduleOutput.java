import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

class ScheduleOutput {
    private ArrayList<TimePart> parts;
    ScheduleOutput(ArrayList<TimePart> tp)
    {
        this.parts = tp;
    }
    private String resultInfo() {
        StringBuilder result = new StringBuilder();
        for (TimePart tp :parts) {
            result.append(tp.info()).append(": ").append("\n");
            for (Lecture lect : tp.getLectures()) {
                result.append(lect.toString());
                result.append("\n");
            }
        }
        return result.toString();
    }
    void printResult()
    {
        System.out.print(resultInfo());
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
        HSSFSheet daysSheet = workbook.createSheet("Lectures ScheduleAlgorithm");

        HSSFRow row = daysSheet.createRow(0);
        CellStyle parts_style = workbook.createCellStyle();
        parts_style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        parts_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        CellStyle days_style = workbook.createCellStyle();
        days_style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        days_style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        for (int curPart = 1; curPart <= TimePart.PARTS_PER_DAY; curPart++) {
            HSSFCell cell = row.createCell(curPart);
            cell.setCellValue("Part " + curPart);
            cell.setCellStyle(parts_style);
        }
        for (int curday = 1; curday <= parts.get(parts.size() - 1).getDay(); curday++) {
            row = daysSheet.createRow(curday);
            HSSFCell days_cell = row.createCell(0);
            days_cell.setCellValue("Day" + curday);
            days_cell.setCellStyle(days_style);
            for (int curPart = 1; curPart <= TimePart.PARTS_PER_DAY; curPart++) {
                StringBuilder stringBuilder = new StringBuilder();
                for (TimePart tp : parts) {
                    if (tp.getDay() == curday && tp.getPart() == curPart) {
                        Iterator<Lecture> iterator = tp.getLectures().iterator();
                        while (iterator.hasNext())
                        {
                            Lecture lecture = iterator.next();
                            stringBuilder.append(lecture.toString());
                            if (iterator.hasNext())
                            {
                                    stringBuilder.append("\n");
                            }
                        }
                    }
                }
                HSSFCell cell = row.createCell(curPart);
                cell.setCellValue(stringBuilder.toString());
                CellStyle style = workbook.createCellStyle();
                style.setAlignment(CellStyle.ALIGN_CENTER);
                style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
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

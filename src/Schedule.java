import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by pouryafard on 1/15/2017 AD.
 */
public class Schedule {
    private ArrayList<Lecture> lectures;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;
    private ArrayList<ArrayList<Teacher>> available;
    private ArrayList<TimePart> parts;
    Schedule() {
        lectures = new ArrayList<>();
        teachers = new ArrayList<>();
        subjects = new ArrayList<>();
        parts = new ArrayList<>();
    }
    private ArrayList<Teacher> AllTeachersOnSubject(Subject s) {
        ArrayList<Teacher> result = new ArrayList<>();
        for(Teacher t:teachers)
            if (t.getSubjects().contains(s)) result.add(t);
        return result;
    }
    private Subject findSubjectByName(String name) {
        for(Subject sub:subjects)
            if(sub.getName().equals(name))
                return sub;
        return null;
    }
    private Teacher findTeacherByName(String name){
        for(Teacher teacher:teachers)
            if(teacher.getName().equals(name))
                return teacher;
        return null;
    }
    private Lecture findLectureByName(String name){
        for(Lecture lect:lectures)
            if(lect.getName().equals(name))
                return lect;
        return null;
    }
    void exportExcel(){
        try {
            initDataForExcel();
            makeExcel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initDataForExcel() throws FileNotFoundException {
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
    private void initSubjects() throws FileNotFoundException {
        Scanner subjectInput = new Scanner(new File("Subjects.txt"));
        while (subjectInput.hasNext())
            subjects.add(new Subject(subjectInput.next()));
        subjectInput.close();
    }
    private void initTeachers() throws Exception {
        Scanner teacherInput = new Scanner(new File("Teachers.txt"));
        while (teacherInput.hasNext())
        {
            Teacher teacher = new Teacher(teacherInput.next());
            int subjectCount = teacherInput.nextInt();
            for (int i = 0; i < subjectCount; i++) {
                String subjectname = teacherInput.next();
                Subject subject = findSubjectByName(subjectname);
                if (subject == null)
                    throw new Exception("No Subject found with the name :" + subjectname);
                teacher.addSubject(subject);
                subject.addTeacher(teacher);
            }
        }
        teacherInput.close();
    }
    private void initLectures() throws Exception {
        Scanner lecInput = new Scanner(new File("Lectures.txt"));
        while (lecInput.hasNext()){
            String lecname = lecInput.next();
            String teachername = lecInput.next();
            Teacher supervisor = findTeacherByName(teachername);
            if (supervisor == null)
                throw new Exception("No Teacher found with the name :" + teachername);
            Lecture lec = new Lecture(lecname,supervisor);
            int lectureCount = lecInput.nextInt();
            for (int i = 0; i < lectureCount; i++) {
                String subjectname = lecInput.next();
                Subject sub = findSubjectByName(subjectname);
                if (sub == null)
                    throw new Exception("No Subject found with the name :" + subjectname);
                lec.addSubject(sub);
            }
            lectures.add(lec);
        }
        lecInput.close();
    }
    void initializer(){
        try {
            initSubjects();
            initTeachers();
            initLectures();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Problem with initializer");
        }
    }
    public ArrayList<Lecture> unAssignedLectures() {
        ArrayList<Lecture> unassigned = new ArrayList<>();
        for(Lecture lec:lectures)
            if (!lec.isAssigned()) unassigned.add(lec);
        return unassigned;
    }
    public TimePart getLastTimePart()
    {
        return parts.get(parts.size() - 1);
    }
    Lecture FindPossiblities(ArrayList<Lecture> notAssigned)
    {
        //TODO : FindPossiblities
      return null;
    }
    void Solve()
    {
        int _day = 1;
        int _part = 1;
        new TimePart(_day,_part);
        while(true)
        {
            ArrayList<Lecture> notAssigned = unAssignedLectures();
            if (notAssigned.isEmpty()) {
                //TODO: Assignment Completed
                return;
            }
            Lecture lect = FindPossiblities(notAssigned);
            if (lect == null) parts.add(TimePart.next(_day,_part));
            else
            {
                getLastTimePart().addLecture(lect);
            }
        }
    }
}

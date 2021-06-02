import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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
    void exportExcel(){
        try {
            initDataForExcel();
            makeExcel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initDataForExcel() throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File("Sample Output.txt"));
//        while (scanner.hasNext()){
//            Lecture lec = Lecture.findByName(scanner.next());
//            Teacher referee1 = Teacher.findByName(scanner.next());
//            Teacher referee2 = Teacher.findByName(scanner.next());
//            int day = scanner.nextInt();
//            int part = scanner.nextInt();
//            lec.clearReferees();
//            lec.addReferee(referee1);
//            lec.addReferee(referee2);
//            TimePart timePart = TimePart.get(day,part);
//            timePart.lectures.add(lec);
//        }
//        System.out.println("Successful initialize!");
    }
    private void makeExcel() throws IOException {
        //TODO : use correct outputs;
//        String exportAdr = "Schedule.xls";
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        //////////////////////////////////Days Sheet////////////////////////////////////
//        HSSFSheet daysSheet = workbook.createSheet("Days");
//        for(int dayCounter =1; dayCounter<= TimePart.lastDay();dayCounter++){
//            for(int partCounter =1 ; partCounter<=4;partCounter++){
//                int rowNumber = (dayCounter-1)*4 +(partCounter-1);
//                HSSFRow currentRow = daysSheet.createRow(rowNumber);
//                currentRow.createCell(0).setCellValue("day"+dayCounter+ " part"+partCounter);
//                int lectureCounter =1;
//                TimePart currentTimePart = TimePart.get(dayCounter,partCounter);
//                for(Lecture walk:currentTimePart.lectures){
//                    currentRow.createCell(lectureCounter).setCellValue(walk.print());
//                    lectureCounter++;
//                }
//            }
//        }
//        for(int i=0;i<10;i++){
//            daysSheet.autoSizeColumn(i);
//        }
//
//        //////////////////////////////////Teachers Sheet////////////////////////////////////
//        HSSFSheet teachersSheet = workbook.createSheet("Teachers");
//        HSSFRow firstRow = teachersSheet.createRow(0);
//        firstRow.createCell(0).setCellValue("Teacher");
//        for(int dayCounter=1;dayCounter<=TimePart.lastDay();dayCounter++){
//            for (int partCounter = 1; partCounter <= 4; partCounter++) {
//                int cellNumber = (dayCounter-1)*4 +partCounter;
//                firstRow.createCell(cellNumber).setCellValue("day"+dayCounter+" part"+partCounter);
//            }
//        }
//        int rowCounter=1;
//        for(Teacher walk:Teacher.list){
//            HSSFRow currentRow = teachersSheet.createRow(rowCounter);
//            currentRow.createCell(0).setCellValue(walk.toString());
//            ArrayList<HSSFCell> cells=new ArrayList<>();
//            for(int dayCounter=1;dayCounter<=TimePart.lastDay();dayCounter++){
//                for (int partCounter = 1; partCounter <= 4; partCounter++) {
//                    int cellNumber = (dayCounter-1)*4 +partCounter;
//                    cells.add(currentRow.createCell(cellNumber));
//                }
//            }
//            for(Lecture lecture:Lecture.findByParticipator(walk)){
//                TimePart timePart = lecture.getTimePart();
//                int cellNumber = (timePart.getDay()-1)*4 + timePart.getPart();
//                HSSFCell cell =  cells.get(cellNumber-1);
//                String lastContent = cell.getStringCellValue();
//                cell.setCellValue(lastContent + lecture.print());
//            }
//            rowCounter++;
//        }
//        for(int i=0;i<10;i++){
//            teachersSheet.autoSizeColumn(i);
//        }
//
//        //////////////////////////////////Write to file////////////////////////////////////
//        FileOutputStream outputFile = new FileOutputStream(exportAdr);
//        workbook.write(outputFile);
//        outputFile.close();
//        System.out.println("Excel successfully generated!");
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
                    throw new Exception("No Subject found with the name : " + subjectname);
                teacher.addSubject(subject);
                subject.addTeacher(teacher);
            }
            teachers.add(teacher);
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
                throw new Exception("No Teacher found with the name : " + teachername);
            Lecture lec = new Lecture(lecname,supervisor);
            int lectureCount = lecInput.nextInt();
            for (int i = 0; i < lectureCount; i++) {
                String subjectname = lecInput.next();
                Subject sub = findSubjectByName(subjectname);
                if (sub == null)
                    throw new Exception("No Subject found with the name : " + subjectname);
                lec.addSubject(sub);
            }
            lectures.add(lec);
        }
        lecInput.close();
    }
    private void printTeachers()
    {
        for (Teacher t1: teachers) System.out.println(t1.getName());
    }
    private void printLectures() {
        for (Lecture l:  lectures)
            System.out.println(l);
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
    private ArrayList<Lecture> unAssignedLectures() {
        ArrayList<Lecture> unassigned = new ArrayList<>();
        for(Lecture lec:lectures)
            if (!lec.isAssigned()) unassigned.add(lec);
        return unassigned;
    }
    private TimePart getLastTimePart()
    {
        return parts.get(parts.size() - 1);
    }
    private Lecture FindPossibleMatching(ArrayList<Lecture> notAssigned, PriorityQueue<Teacher> possibleTeachers)
    {

        for (Lecture lecture: notAssigned) {
            Set<Teacher> experts = new HashSet<>();
            for (Subject sub : lecture.getSubjects())
                experts.addAll(findExperts(sub, possibleTeachers));
            experts.remove(lecture.getSupervisor());
            if (experts.size() >= 2) {
                Iterator<Teacher> it = experts.iterator();
                lecture.addReferee(it.next());
                lecture.addReferee(it.next());
                return lecture;
            }
        }
        return null;
    }
    private Set<Teacher> findExperts(Subject sub, PriorityQueue<Teacher> possibleTeachers) {
        Set<Teacher> result = new HashSet<>();
        for (Teacher t: possibleTeachers)
            if (t.isExpertInSubject(sub)) result.add(t);
        return result;
    }
    private Set<Teacher> calculateBusyTeachers()
    {
        TimePart last = getLastTimePart();

        Set<Teacher> busyTeachers = new HashSet<>();
        ArrayList<Lecture> curLectures = last.getLectures();
        for (Lecture lec: curLectures) {
                busyTeachers.add(lec.getSupervisor());
                busyTeachers.addAll(lec.getReferees());
        }
        return busyTeachers;
    }
    private PriorityQueue<Teacher> calculateFreeTeachers()
    {
        PriorityQueue<Teacher> freeTeachers = new PriorityQueue<>(Comparator.comparingInt(Teacher::getSubjectsSize));
        Set<Teacher> busyTeachers = calculateBusyTeachers();
        for (Teacher t : teachers) {
            if (!busyTeachers.contains(t)) freeTeachers.add(t);
        }
        return freeTeachers;
    }
    void printResult()
    {
        System.out.println(resultInfo());
    }
    private String resultInfo()
    {
        StringBuilder result = new StringBuilder();
        for ( TimePart tp: parts) {
            result.append(tp.info()).append(": ").append("\n");
            for (Lecture l : tp.getLectures()) result.append(l.toString()).append("\n");
        }
        return result.toString();
    }
    void solve()
    {
        int _day = 1;
        int _part = 1;
        TimePart curPart = new TimePart(_day,_part);
        parts.add(curPart);
        while(true)
        {
            ArrayList<Lecture> notAssigned = unAssignedLectures();
            if (notAssigned.isEmpty()) {
                printResult();
                break;
            }
            PriorityQueue<Teacher> FreeTeachers = calculateFreeTeachers();
            Lecture lect = FindPossibleMatching(notAssigned,FreeTeachers);
            if (lect == null)
                {
                    curPart = TimePart.next(curPart);
                    parts.add(curPart);
                }
            else
            {
                getLastTimePart().addLecture(lect);
            }


        }
    }
}

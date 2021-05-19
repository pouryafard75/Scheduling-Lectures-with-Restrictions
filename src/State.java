/**
 * Created by pouryafard on 1/15/2017 AD.
 */

import java.sql.Time;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Scanner;
public class State {
    public int Dates; // fitness
    public ArrayList<TimePart> times;
    public String result = "";


    State() {
        times = new ArrayList<>();
        ClearRefrees();
        Fill();
        Dates = times.get(times.size() - 1).day;
        CalculateResult();
    }

    public String getAllInfo()
    {
        String result = "";
        for (TimePart tp : times) {
            for (Lecture lec : tp.lectures) {
                result += lec.print();
                result += lec.output();
                result += " " + tp.day + " " + tp.part;
                result += "\r\n";
            }
        }
        return result;
    }

    State(ArrayList<TimePart> tp) {
        times = tp;
    }

    public void ClearRefrees() {
        for (Lecture lt : Lecture.list)
            lt.referees.clear();
    }

    void CalculateResult() {
        for (TimePart tp : times) {
            for (Lecture lec : tp.lectures) {
                result += lec.output();
                result += " " + tp.day + " " + tp.part;
                result += "\r\n";
            }

        }
    }

    public void Fill() {
        int _day = 1;  //firstday
        int _part = 1;  //firstpart
        boolean timeChanged = false;
        TimePart tp;
        times.add(new TimePart(1,1));
        PriorityQueue<Teacher> possibleTeachers = new PriorityQueue<>((o1, o2) -> o1.subjects.size() - o2.subjects.size());
        ArrayList<Teacher> tempTeachers = new ArrayList<>();
        for (int i = 0; i < Lecture.list.size(); i++) {
            boolean nope = false;
            tp = times.get(times.size() - 1);
            if (timeChanged == true)
            {
                timeChanged = false;
                i = 0;
                continue;
            }
            tempTeachers.clear();
            possibleTeachers.clear();
            Lecture currentLec = Lecture.list.get(i);
            if (!currentLec.referees.isEmpty()) {
                continue;
            }
            for (Subject s : currentLec.subjects) {
                tempTeachers = Teacher.BySubject(s);
                for (Teacher teacher : tempTeachers) {
                    if (!possibleTeachers.contains(teacher))
                        possibleTeachers.add(teacher);
                    possibleTeachers.remove(currentLec.supervisor);
                    for (Lecture lecture : tp.lectures)
                    {
                        if (lecture.supervisor == currentLec.supervisor)
                            nope = true;
                    }
                    for (Lecture le : tp.lectures)
                    {
                        if (!le.referees.isEmpty()) {
                            possibleTeachers.remove(le.referees.get(0));
                            possibleTeachers.remove(le.referees.get(1));
                        }
                        possibleTeachers.remove(le.supervisor);
                    }
                }
            }
            //Pick Two Teacher
            if (!nope && possibleTeachers.size() >= 2) {
                currentLec.referees.add(possibleTeachers.poll());
                currentLec.referees.add(possibleTeachers.poll());
                tp.lectures.add(currentLec);
            } else {
                nope = false;
                for (int l = 0; l < Lecture.list.size(); l++) {
                    possibleTeachers.clear();
                    tempTeachers.clear();
                    Lecture replacement = Lecture.list.get(l);
                    if (replacement.referees.isEmpty() && replacement != currentLec) {
                        for (Subject s : replacement.subjects) {
                            tempTeachers = Teacher.BySubject(s);
                            for (Teacher teacher : tempTeachers) {
                                if (!possibleTeachers.contains(teacher))
                                    possibleTeachers.add(teacher);
                            }
                        }
                        possibleTeachers.remove(replacement.supervisor);
                        for (Lecture lecture : tp.lectures)
                        {
                            if (lecture.supervisor == replacement.supervisor)
                                nope = true;
                        }
                        for (Lecture le : tp.lectures)
                        {
                            possibleTeachers.remove(le.referees.get(0));
                            possibleTeachers.remove(le.referees.get(1));
                            possibleTeachers.remove(le.supervisor);
                        }
                        //Pick Two Teacher
                        if (!nope && possibleTeachers.size() >= 2) {
                            replacement.referees.add(possibleTeachers.poll());
                            replacement.referees.add(possibleTeachers.poll());
                            tp.lectures.add(replacement);
                            //System.out.println(replacement.name + replacement.referees.get(0).name + "&" + replacement.referees.get(1).name);
                            possibleTeachers.clear();
                            tempTeachers.clear();
                        }
                    }
                }
                if (_part == 4) {
                    _part = 1;
                    _day++;
                } else _part++;
                timeChanged = true;
                times.add(new TimePart(_day, _part));
                tempTeachers.clear();
                possibleTeachers.clear();
            }
        }
        for (Lecture lect : Lecture.list) {
            boolean inserted = false;
            if (lect.referees.isEmpty()) {
                for (TimePart tps : times) {
                    for (Subject s : lect.subjects) {
                        tempTeachers = Teacher.BySubject(s);
                        for (Teacher teacher : tempTeachers) {
                            if (!possibleTeachers.contains(teacher))
                                    possibleTeachers.add(teacher);
                            possibleTeachers.remove(lect.supervisor);
                            for (Lecture le : tps.lectures)
                            {
                                if (!le.referees.isEmpty()) {
                                    possibleTeachers.remove(le.referees.get(0));
                                    possibleTeachers.remove(le.referees.get(1));
                                }
                                possibleTeachers.remove(le.supervisor);
                            }

                        }
                    }
                    if (possibleTeachers.size() > 2) {
                        lect.referees.add(possibleTeachers.poll());
                        lect.referees.add(possibleTeachers.poll());
                        tps.lectures.add(lect);
                        possibleTeachers.clear();
                        tempTeachers.clear();
                        inserted = true;
                        break;
                    }
                    possibleTeachers.clear();
                    tempTeachers.clear();
                }
                if (!inserted) {
                    int lastday = times.get(times.size() - 1).day;
                    int lastpart = times.get(times.size() - 1).part;
                    if (lastpart != 4)
                        lastpart++;
                    else {
                        lastday++;
                        lastpart = 1;
                    }

                    tp = new TimePart(lastday, lastpart);
                    tp.lectures.add(lect);
                    times.add(tp);
                }
            }
        }
    }
}

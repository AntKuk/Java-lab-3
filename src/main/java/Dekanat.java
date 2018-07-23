import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;


class Dekanat {
    private ArrayList<Student> Students;
    private ArrayList<Group> Groups;

    Dekanat () {
        this.Students = new ArrayList<Student>();
        this.Groups = new ArrayList<Group>();
    }

    public ArrayList<Student> getStudents() {
        return Students;
    }

    public ArrayList<Group> getGroups() {
        return Groups;
    }

    void printGTitleAndStName () {
        if ( Groups.size() != 0) {
            System.out.println("Группы: ");
            for (Group g : Groups) {
                System.out.println("- " + g.getTitle());
                System.out.println("- - Староста " + g.getHead());
                for (Student s : g.getStudents()) {
                    System.out.println("--" + s.getID() + " " + s.getFio());
                }
                System.out.println();
            }
        }
    }


    //Reading JSON, creating Groups and Students
    void importJSON() {
        JSONParser parser = new JSONParser();
        URL file = System.class.getResource("/Students");
        try {
            //Object obj = parser.parse(new FileReader("./src/main/resources/Students"));
            Object obj = parser.parse(new FileReader(new File(file.getFile())));
            JSONArray stds = (JSONArray) obj;
            Iterator stdIterator = stds.iterator();
            while (stdIterator.hasNext()) {
                JSONObject std = (JSONObject) stdIterator.next();
                String title = std.get("title").toString();
                this.Groups.add (new Group (title));
                JSONArray students = (JSONArray) std.get("students");
                for (Object c : students) {
                    JSONObject a = (JSONObject) c;
                    String fio = a.get("fio").toString();
                    int id = Integer.parseInt(a.get("id").toString());
                    for(Group g: Groups) {
                        if (title.equals(g.getTitle())) {
                            this.Students.add(new Student (id, fio, g));
                        }
                    }
                }

            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (ParseException ex) {
        }

        for (Group g: Groups) {
            for (Student s: Students) {
                if(g.getTitle().equals(s.getGroupTitle())) {
                    g.addStudent(s);
                }
            }
        }
    }

    void setMarks () {
        for (Student s: Students) {
            for (int i = 0; i < 11; i++) {
                s.setMark(ThreadLocalRandom.current().nextInt(2, 5 + 1));
            }
        }
    }

    void setHeads () {
        for (Group g: Groups) {
            g.setHead();
        }
    }

    void Statistica () {
        System.out.println("Всего групп - " + Groups.size());
        System.out.println("Всего студентов - " + Students.size());
        for (Group g: Groups) {
            System.out.println("Средняя оценка группы " + g.getTitle() + " - " + g.getAvgGroupMark());

            //Реализованное 5 лучших и 5 худших студентов
            ArrayList<Student> sorted= sorting(g.getStudents());
            System.out.println("5 самых слабых студентов группы " + g.getTitle() + ":");
            for (int i = 0; i <5; i++) {
                System.out.print(sorted.get(i).getFio() + " - ");
                System.out.format("%.3f",sorted.get(i).getAvgMark());
                System.out.println();
            }
            System.out.println("5 самых умных студентов группы " + g.getTitle() + ":");
            for (int i = g.getStudents().size()-1; i > g.getStudents().size() - 6; i--) {
                System.out.print(sorted.get(i).getFio() + " - ");
                System.out.format("%.3f",sorted.get(i).getAvgMark());
                System.out.println();
            }

            System.out.println();

        }
    }

    //Сортировка студентов по успеваемости
    private ArrayList <Student> sorting (ArrayList<Student> original) {
        ArrayList <Student> copy = (ArrayList <Student>) original.clone();
        Student t = null;
        for (int a = 1; a < copy.size(); a++) {
            for (int b = copy.size()-1; b >= a; b--) {
                if (copy.get(b-1).getAvgMark() > copy.get(b).getAvgMark() ) {
                    t = copy.get(b-1);
                    copy.set(b-1, copy.get(b));
                    copy.set (b, t);
                }
            }
        }
        return copy;
    }

    void changeGroup (String student, String group) {

        for (Student s: Students) {
            if (student.equals(s.getFio())) {
                s.getGroup().delStudent(s);
                for(Group g : Groups) {
                    if (group.equals(g.getTitle())) {
                        s.setGroup(g);
                        g.addStudent(s);
                    }
                }
            }
        }

    }

    void delStudentsForBadMarks () {
        ArrayList<Student> BadMarksStudents = new ArrayList<Student>();
        for (Student s: Students) {
            if(s.getAvgMark() < 3.0) {
                System.out.println("Студент " + s.getFio() + " отчислен за неуспеваемость");
                s.getGroup().delStudent(s);
                s.setGroup(null);
                BadMarksStudents.add (s);
            }
        }
        for (Student s: BadMarksStudents) {
            if (s.getGroup() == null) {
                Students.remove (s);
            }
        }
        System.out.println();
    }

    void printAvg () {
        for (Student s: Students) {
            System.out.println(s.getFio() +" - " + s.getAvgMark());
        }
    }

    void setIDs () {
        for (int i = 0; i < Students.size(); i++) {
            Student s = Students.get(i);
            s.setID(i);
        }
    }

    void saveJSON () {
        JSONArray listMain = new JSONArray();


        for (Group g : Groups) {
            JSONObject obj1 = new JSONObject();
            String title = g.getTitle();
            obj1.put("title", title);
            JSONArray list1 = new JSONArray();
            for (Student s : g.getStudents()) {
                JSONObject obj2 = new JSONObject();
                String fio = s.getFio();
                int id = s.getID();
                obj2.put("fio", fio);
                obj2.put("id", id);
                list1.add(obj2);
            }
            obj1.put("students", list1);
            listMain.add(obj1);
        }

        try {

            try (FileWriter file = new FileWriter("test.json")) {
                file.write(listMain.toJSONString());
                file.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.print(listMain);

    }



}

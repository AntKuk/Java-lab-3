import java.util.ArrayList;
import java.util.Random;

public class Group {
    private String Title;
    private ArrayList<Student> Students;
    private Student Head;

    Group (String title) {
        this.Title = title;
        this.Students = new ArrayList<Student>();
        this.Head = null;
    }

    void addStudent (Student student) {
        Students.add(student);
    }

    void setHead () {
        Random rand = new Random();
        int numHead = rand.nextInt(Students.size());
        this.Head = Students.get(numHead);
    }

    String getHead () {
        return Head.getFio();
    }

    boolean searchStudent (String fio) {
        for (Student s : Students) {
            if (fio.equals(s.getFio())) {
                System.out.println ("Student ID is " + s.getID());
                return true;
            }
        }
        return false;
    }

    boolean searchStudent (int ID) {
        for (Student s : Students) {
            if (ID == s.getID()) {
                System.out.println ("Student name is " + s.getFio());
                return true;
            }
        }
        return false;
    }

    double getAvgGroupMark () {
        double sum = 0;
        for (Student s: Students) {
            sum += s.getAvgMark();
        }
        return sum/Students.size();
    }

    void delStudent (Student del) {
        Students.remove(del);
    }

    String getTitle () {
        return this.Title;
    }



    ArrayList <Student> getStudents () {
        return this.Students;
    }


}

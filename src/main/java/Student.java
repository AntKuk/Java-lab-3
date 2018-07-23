import java.util.ArrayList;

class Student {
    private int ID;
    private String Fio;
    private Group group;
    private ArrayList<Integer> marks;

    Student (int ID, String Fio, Group group) {
        this.ID = ID;
        this.Fio = Fio;
        this.group = group;
        this.marks = new ArrayList<Integer> ();
    }

    String getFio () {
        return this.Fio;
    }

    int getID () {
        return this.ID;
    }
    void setGroup (Group group) {
        this.group = group;
    }

    String getGroupTitle () {
        return group.getTitle();
    }

    Group getGroup () { return this.group;}

    void setMark (int mark) {
        marks.add(mark);
    }

    double getAvgMark () {
        Integer sum = 0;
        for (Integer i: marks) {
            sum += i;
        }

        return (double) sum/marks.size();
    }

    void setID (int number) {
        this.ID = number;
    }
}

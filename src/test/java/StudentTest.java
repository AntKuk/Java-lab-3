import static org.junit.Assert.*;

public class StudentTest {

    @org.junit.Test
    public void getAvgMark() {
        Student test = new Student(15, "Anton", null);
        test.setMark(5);
        test.setMark(3);
        test.setMark(4);
        assertEquals(4.0, test.getAvgMark(), 0.0001);

    }
}
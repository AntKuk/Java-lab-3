public class DekanatDemo {
    public static void main(String[] args) {
        Dekanat dek = new Dekanat();
        dek.importJSON();
        dek.setMarks();
        dek.setHeads();
        dek.setIDs();

        dek.printGTitleAndStName();

        dek.changeGroup("Жариков Фока Левович", "СНВ15");

        dek.Statistica();

        dek.delStudentsForBadMarks();
        dek.printGTitleAndStName();

        dek.saveJSON();





        System.out.println("End");
    }
}

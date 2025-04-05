import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Person p1 = new Person("Jan", "Kowalski", LocalDate.of(1980, 1, 12), LocalDate.of(2045, 1, 12));
//        Person p2 = new Person("Kamil", "Nowak", LocalDate.of(1990, 11, 22), LocalDate.of(2045, 11, 22));
//        Person p3 = new Person("Bartłomiej", "Daniluk", LocalDate.of(1999, 1, 17), LocalDate.of(2112, 1, 17));
//
//        List<Person> people = new ArrayList<>();
//        people.add(p1);
//        people.add(p2);
//        people.add(p3);
//
//        p1.adopt(p2);
//        p1.adopt(p3);
//
//        System.out.println(p1.getYoungestChild());
//        System.out.println(p1.getChildren().get(0));
//
//        Family fam1 = new Family();
//        fam1.add(p1, p2, p3);
//
//        System.out.println(Arrays.toString(fam1.get("Jan Kowalski")));
//
//
//
//
//
//
//
////        List<Person> people = List.of(
////                new Person("Jan", "Kowalski", LocalDate.of(1980, 1, 12)),
////                new Person("Kamil", "Nowak", LocalDate.of(1990, 11, 22)),
////                new Person("Bartłomiej", "Daniluk", LocalDate.of(1999, 1, 17))
//        );

        System.out.println("--- Zadanie 1: Testowanie fromCsvLine() ---");
        try {
            String line1 = "Jan,Kowalski,1980-05-15,2020-10-20,Adam,Kowalski,Ewa,Kowalska";
            Person person1 = Person.fromCsvLine(line1);
            System.out.println(person1);

            String line2 = "Anna,Nowak,1995-08-22,,Jan,Nowak,Maria,Nowak";
            Person person2 = Person.fromCsvLine(line2);
            System.out.println(person2);
        } catch (NegativeLifespanException e) {
            System.err.println("Błąd: " + e.getMessage());
        }

//        // Zadanie 2: Testowanie fromCsv()
//        System.out.println("\n--- Zadanie 2: Testowanie fromCsv() ---");
//        List<Person> people = Person.fromCsv("people.csv"); // Utwórz plik people.csv z danymi
//        for (Person person : people) {
//            System.out.println(person);
//        }
//
//        // Zadanie 3: Testowanie NegativeLifespanException
//        System.out.println("\n--- Zadanie 3: Testowanie NegativeLifespanException ---");
//        try {
//            String line3 = "Piotr,Wiśniewski,1990-01-01,1980-01-01,Jan,Wisniewski,Anna,Wisniewska";
//            Person.fromCsvLine(line3); // Powinno rzucić NegativeLifespanException
//        } catch (NegativeLifespanException e) {
//            System.err.println("Błąd: " + e.getMessage());
//        }
//
//        // Zadanie 4: Testowanie AmbiguousPersonException
//        System.out.println("\n--- Zadanie 4: Testowanie AmbiguousPersonException ---");
//        Person.fromCsv("ambiguous.csv"); // Utwórz plik ambiguous.csv z duplikatami
//
//        // Zadanie 5: Testowanie ustawiania referencji dzieci
//        System.out.println("\n--- Zadanie 5: Testowanie ustawiania referencji dzieci ---");
//        List<Person> people = Person.fromCsv("children.csv"); // Utwórz plik children.csv z danymi rodziców
//        for (Person person : people) {
//            System.out.println(person);
//        }
//        for (Person person : people) {
//            if(person.getName().equals("Adam") && person.getSurname().equals("Kowalski")){
//                System.out.println("Dzieci Adama Kowalskiego: " + person.getChildren());
//            }
//        }
//
//        // Zadanie 6: Testowanie ParentingAgeException
//        System.out.println("\n--- Zadanie 6: Testowanie ParentingAgeException ---");
//        List<Person> people = Person.fromCsv("parenting.csv"); // Utwórz plik parenting.csv z błędnymi danymi rodziców
//        for (Person person : people) {
//            System.out.println(person);
//        }
//
//        // Zadanie 7: Testowanie zapisu i odczytu binarnego
//        System.out.println("\n--- Zadanie 7: Testowanie zapisu i odczytu binarnego ---");
//        List<Person> peopleToBinary = new ArrayList<>();
//        peopleToBinary.add(new Person("Karol", "Nowak", LocalDate.of(2000, 1, 1), null));
//        peopleToBinary.add(new Person("Zofia", "Kowalska", LocalDate.of(1985, 5, 10), LocalDate.of(2022, 12, 25)));
//
//        Person.toBinaryFile(peopleToBinary, "people.bin");
//        List<Person> readPeopleBinary = Person.fromBinaryFile("people.bin");
//        for (Person person : readPeopleBinary) {
//            System.out.println(person);
//        }
//
//        // Zadanie 7 Alternatywne: testowanie zapisu i odczytu csv
//        System.out.println("\n--- Zadanie 7: Testowanie zapisu i odczytu binarnego ---");
//        List<Person> peopleToBinary = new ArrayList<>();
//        peopleToBinary.add(new Person("Karol", "Nowak", LocalDate.of(2000, 1, 1), null));
//        peopleToBinary.add(new Person("Zofia", "Kowalska", LocalDate.of(1985, 5, 10), LocalDate.of(2022, 12, 25)));
//
//        Person.toBinaryFile(peopleToBinary, "people.bin");
//        List<Person> readPeopleBinary = Person.fromBinaryFile("people.bin");
//        for (Person person : readPeopleBinary) {
//            System.out.println(person);
//        }



    }
}
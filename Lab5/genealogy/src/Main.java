import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Jan", "Kowalski", LocalDate.of(1980, 1, 12));
        Person p2 = new Person("Kamil", "Nowak", LocalDate.of(1990, 11, 22));
        Person p3 = new Person("Bartłomiej", "Daniluk", LocalDate.of(1999, 1, 17));

        List<Person> people = new ArrayList<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);

        p1.adopt(p2);
        p1.adopt(p3);

        System.out.println(p1.getYoungestChild());
        System.out.println(p1.getChildren().get(0));

        Family fam1 = new Family();
        fam1.add(p1, p2, p3);

        System.out.println(fam1.get("Jan Kowalski"));







//        List<Person> people = List.of(
//                new Person("Jan", "Kowalski", LocalDate.of(1980, 1, 12)),
//                new Person("Kamil", "Nowak", LocalDate.of(1990, 11, 22)),
//                new Person("Bartłomiej", "Daniluk", LocalDate.of(1999, 1, 17))
//        );







    }
}
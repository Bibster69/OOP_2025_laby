import java.time.LocalDate;
import java.util.*;

public class Person implements Comparable<Person>{
    private String name;
    private String surname;

    private LocalDate birthDate;

    private Set<Person> children;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Person(String name, String surname, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.children = new HashSet<>();
    }

    public boolean adopt(Person child) {
        if(child == null || child == this) return false;
        return this.children.add(child);
    }

    @Override
    public String toString(){
        return "Osoba o imieniu " + this.name + " i nazwisku " + this.surname;
    }

    public Person getYoungestChild() {
        if (this.children == null || this.children.isEmpty()) {
            return null;
        }
        Person youngest = this.children.iterator().next();
        for (Person child: this.children) {
            if(youngest.compareTo(child) < 0) {
                youngest = child;
            }
        }
        return youngest;
    }

    @Override
    public int compareTo(Person other) {
//        return this.birthDate.compareTo(other.birthDate);
        if (this.birthDate.isAfter(other.birthDate)) {
            return 1; // other starszy
        } else if (this.birthDate.isBefore(other.birthDate)) {
            return -1; // other młodszy
        } else {
            return 0; // other równi
        }
    }

    public List<Person> getChildren(){
        List<Person> sortedChildren = new ArrayList<>(this.children);
        Collections.sort(sortedChildren, Collections.reverseOrder());
        return sortedChildren;
    }

//    Collections.sort(al, Collections.reverseOrder());

}

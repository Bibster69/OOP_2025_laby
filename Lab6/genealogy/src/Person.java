import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Person implements Comparable<Person>, Serializable {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Set<Person> children;


    public Person(String name, String surname, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.children = new HashSet<>();
    }

    public Set<Person> getChildren() {
        return children;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean adopt(Person person){
        if (person == null || person == this) return false;
        for (Person child : person.getChildren()) {
            if (child == this){
                return false;
            }
        }
        return this.children.add(person);
    }

    public Person getYoungestChild(){
        if(this.children == null || this.children.isEmpty()) return null;
        Person youngest = this.children.iterator().next();

        for (Person child : this.children) {
            if (youngest.compareTo(child) < 0) youngest = child;
        }
        return youngest;
    }

    @Override
    public String toString(){
        return "Hejka mam na imie " + this.name + " a na nazwisko " + this.surname;
    }

    @Override
    public int compareTo(Person other) {
        if (this.birthDate.isAfter(other.getBirthDate())) {
            return 1;
        } else if (this.birthDate.isBefore(other.getBirthDate())) {
            return -1;
        } else {
            return 0;
        }
    }

    public List<Person> getSortedChildren(){
        List<Person> sortedChildren = new ArrayList<>(this.children);
        Collections.sort(sortedChildren);
        return sortedChildren;
    }

    public static Person fromCsvLine(String line) throws NegativeLifespanException{
        String[] lineParts = line.split(",");
        String name = lineParts[0].split(" ")[0];
        String surname = lineParts[0].split(" ")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(lineParts[1], formatter);
        LocalDate deathDate = null;
        if (!lineParts[2].isEmpty()) {
            deathDate = LocalDate.parse(lineParts[2], formatter);
            System.out.println("Data urodzin = " + birthDate);
            System.out.println("Data śmierci = " + deathDate);
            if (deathDate.isBefore(birthDate)) {
                throw new NegativeLifespanException("Data śmierci osoby " + name + " " + surname + " nie zgadza się");
            }
        }
        return new Person(name, surname, birthDate, deathDate);
    }

    public static List<Person> fromCsv(String path) throws NegativeLifespanException{

        List<Person> ppl = new ArrayList<>();
        Set<String> pplFullNames = new HashSet<>();
        Map<String, Person> pplMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = reader.readLine()) != null) { // 3 - parent1, 4 - parnet2
                Person person = Person.fromCsvLine(line);
                String[] lineParts = line.split(",");
                String fullName = person.getName() + " " + person.getSurname();
                if (pplFullNames.contains(fullName)) {
                    throw new AmbiguousPersonException("W pliku istnieje osoba o takim imieniu");
                }
                if (person != null) {
                    pplFullNames.add(fullName);
                    ppl.add(person);
                    pplMap.put(fullName, person);

                    if (lineParts.length > 3){
                        if (!lineParts[3].isEmpty()) {
                            Person parent1 = pplMap.get(lineParts[3]);
                            if (parent1 != null) {
                                try {
                                    checkParentngAge(parent1, person);
                                    parent1.adopt(person);
                                } catch (ParentingAgeException e) {
                                    System.out.println("Błąd " + e.getMessage());
                                    System.out.println("Czy na pewno dodać?");
                                    Scanner scanner = new Scanner(System.in);
                                    String input = scanner.nextLine();
                                    if (input.equalsIgnoreCase("y")) {
                                        parent1.adopt(person);
                                    }
                                }

                            }
                        }
                    }

                    if (lineParts.length > 4) {
                        if (!lineParts[4].isEmpty()) {
                            Person parent2 = pplMap.get(lineParts[4]);
                            if (parent2 != null) {
                                try {
                                    checkParentngAge(parent2, person);
                                    parent2.adopt(person);
                                } catch (ParentingAgeException e) {
                                    System.out.println("Błąd " + e.getMessage());
                                    System.out.println("Czy na pewno dodać?");
                                    Scanner scanner = new Scanner(System.in);
                                    String input = scanner.nextLine();
                                    if (input.equalsIgnoreCase("y")) {
                                        parent2.adopt(person);
                                    }
                                }
                            }
                        }
                    }

                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Nie znaloeziono pliku w metodzie fromCsv w klasie Pearson");
        } catch (IOException e) {
            System.out.println("Błąd IOException w metodzie fromCsv w klasie Pearson");
        } catch (AmbiguousPersonException e) {
            throw new RuntimeException(e);
        }
        return ppl;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public static void checkParentngAge(Person parent, Person child) throws ParentingAgeException {
        if (parent.getBirthDate().plusYears(15).isAfter(child.getBirthDate())) {
            throw new ParentingAgeException("Rodzic jest młodszy niz 15 lat");
        }
        if (parent.getDeathDate() != null && parent.getDeathDate().isBefore(child.getBirthDate())) {
            throw new ParentingAgeException("Rodzic nie żyje w chwili urodzin dziecka");
        }
    }


    public static void toBinaryFile(List<Person> persons, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(persons);
            System.out.println("Zapisano osoby do pliku binarnego: " + path);
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisu do pliku binarnego: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Person> fromBinaryFile(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Person>) obj;
            } else {
                System.out.println("Plik nie zawiera listy osób.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Błąd podczas odczytu z pliku binarnego: " + e.getMessage());
        }
        return new ArrayList<>();
    }



//    BufferedReader(new FileReader(path))

}



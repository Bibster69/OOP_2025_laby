import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Person implements Comparable<Person>, Serializable {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Set<Person> children;
    private Set<Person> parents;


    public Person(String name, String surname, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.children = new HashSet<>();
        this.parents = new HashSet<>();
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

    public Set<Person> getParents() {
        return parents;
    }

    public boolean adopt(Person person){
        if (person == null || person == this) return false;
        for (Person child : person.getChildren()) {
            if (child == this){
                return false;
            }
        }
        boolean adopted = this.children.add(person);
        if (adopted) {
            person.getParents().add(this);
        }
        return adopted;
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

    // Zadanie 2

    public String toPlantUmlObjectDiagram() {
        String result = "@startuml\n";
        String personId = "p_" + this.name + "_" + this.surname;

        result += "object " + personId + " {\n" + " " + this.name + " " + this.surname + "\n" + "}\n";

        for (Person parent : this.parents) {
            String parentId = "p_" + parent.getName() + "_" + parent.getSurname();

            result += "object " + parentId + " {\n" + " " + parent.getName() + " " + parent.getSurname() + "\n" + "}\n";
            result += personId + " --> " + parentId + " : dziecko\n";
        }
        result += "@enduml\n";

        return result;
    }

    // Zadanie 3

    public static String toPlantUmlObjectDiagram(List<Person> persons) {
        String result = "@startuml\n";

        Map<Person, String> idMap = new HashMap<>();


        // Kaza osoba ma przypisany identyfikator Imie_Nazwisko
        for (Person person : persons) {
            idMap.put(person, "p_" + person.getName() + "_" + person.getSurname());
        }

        // dodajemy wszystkie osoby
        for (Person person : persons) {
            String id = idMap.get(person);
            result += "object " + id + " {\n" + " " + person.getName() + " " + person.getSurname() + "\n" + "}\n";
        }

        // Relacje dziecko rodzic
        for (Person person : persons) {
            for (Person parent : person.getParents()) {
                if (idMap.containsKey(parent)) {
                    result += idMap.get(person) + " --> " + idMap.get(parent) + " : dziecko\n";;
                }

            }
        }
        result += "@enduml\n";
        return result;
    }

    // Zadanie 4

    public static List<Person> filterByNameSubstring(List<Person> persons, String substring) {
        if (substring == null || substring.isEmpty()) {return null;}

        String lowerSubstring = substring.toLowerCase();
        List<Person> result = new ArrayList<>();

        for (Person person : persons) {
            if (person.getName().toLowerCase().contains(lowerSubstring) || person.getSurname().toLowerCase().contains(lowerSubstring)) {
                result.add(person);
            }
        }

        return result;
    }

    // Zadanie 5

    // p -> - lambda, czyli funkcja anonimowa, czyli funkcja zdefiniowana bez imienia.
    // bardzo przydatne gdy chcemy utworzyć jakś prościutką funkcje np a + b

    public static List<Person> sortByBirthYear(List<Person> persons) {
        List<Person> sorted = new ArrayList<>(persons);
        sorted.sort(Comparator.comparing(p -> p.getBirthDate().getYear()));
        return sorted;
    }

    // Zadanie 6

    public long getLifespan(){
        if (deathDate == null) {return -1;}
        return ChronoUnit.DAYS.between(birthDate, deathDate);
    }

    public static List<Person> getDeceasedSortedByLifespan(List<Person> persons) {
        List<Person> sorted = new ArrayList<>(persons);

        for (Person person : persons) {
            if (person.getDeathDate() != null) {
                sorted.add(person);
            }
        }

        Collections.sort(sorted, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Long.compare(p2.getLifespan(), p1.getLifespan());
            }
        });

        return sorted;
    }

    // Zadanie 7

    public static Person getOldestLivingPerson(List<Person> persons) {
        Person oldest = null;

        for (Person person : persons) {
            if (person.getDeathDate() == null) { // żyje
                if (oldest == null || person.getBirthDate().isBefore(oldest.getBirthDate())) {
                    oldest = person;
                }
            }
        }

        return oldest;
    }

    // Zadanie 8

    public String toPlantUMLWithParents(Function<String, String> postProcess) {
        String result = "@startuml\n";
        String personId = "obj" + System.identityHashCode(this);
        String personLine = "object \"" + this.name + " " + this.surname + "\" as" + personId;
        result += postProcess.apply(personLine);
        result += "\n";

        for (Person parent : this.parents) {
            String parentId = "obj" + System.identityHashCode(this);
            String parentLine = "object \"" + parent.getName() + " " + parent.getSurname() + "\" as" + parentId;
            result += postProcess.apply(parentLine);
            result += "\n";
            result += personId + " --> " + parentId + "\n";
        }
        result += "@enduml\n";

        return result;
    }

    // Zadanie 9
    public static String toUML(List<Person> people, Function<String, String> postProcess, Predicate<Person> condition) {
        String result = "@startuml\n";

        Map<Person, String> idMap = new HashMap<>();

        for (Person person : people) {
            String personId = "obj" + System.identityHashCode(person);
            idMap.put(person, personId);
            String personLine = "object \"" + person.getName() + " " + person.getSurname() + "\" as" + personId;

            if (condition.test(person)) {
                personLine = postProcess.apply(personLine);
            }

            result += personLine + "\n";

        }

        for (Person person : people) {
            for (Person child : person.getChildren()) {
                String parentId = idMap.get(person);
                String childId = idMap.get(child);
                result += parentId + " --> " + childId + "\n";
            }
        }

        result += "@enduml\n";
        return result;

    }







//    BufferedReader(new FileReader(path))

}



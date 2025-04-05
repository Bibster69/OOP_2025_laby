import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Person implements Comparable<Person>{
    private String name;
    private String surname;

    private LocalDate birthDate;
    private LocalDate deathDate;

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

    public Person(String name, String surname, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
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

    public static Person fromCsvLine(String line) throws NegativeLifespanException {
        String[] parts = line.split(",");
        String name = parts[0];
        String surname = parts[1];
        LocalDate birthDate = LocalDate.parse(parts[2]);
        LocalDate deathDate = null;
        if(!parts[3].isEmpty()) {
            deathDate = LocalDate.parse(parts[3]);
            if (deathDate.isBefore(birthDate)) {
                throw new NegativeLifespanException(
                        "Data śmierci (" + deathDate + ") jest wcześniejsza niż data urodzenia (" + birthDate + ") dla osoby: " + name + " " + surname);
            }
        }
        return new Person(name, surname, birthDate, deathDate);
    }


    public static List<Person> fromCsv(String path) throws FileNotFoundException {
        List<Person> ppl = new ArrayList<>();
        Map<String, Person> pplMap = new HashMap<>();
        Set<String> names = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(",");
                String parent1Name = parts[4];
                String parent1Surname = parts[5];
                String parent2Name = parts[6];
                String parent2Surname = parts[7];
                Person person = Person.fromCsvLine(line);
                String name = person.getName() + " " + person.getSurname();
                if (names.contains(name)) {
                    throw new AmbiguousPersonException("Istnieje już taka osoba !!! ");
                }
                names.add(name);
                ppl.add(person);
                pplMap.put(name, person);

                if (parent1Name != null && parent1Surname != null && parent2Name != null && parent2Surname != null) {
                    String parent1FullName = parent1Name + " " + parent1Surname;
                    String parent2FullName = parent2Name + " " + parent2Surname;
                    Person parent1 = pplMap.get(parent1FullName);
                    Person parent2 = pplMap.get(parent2FullName);
                    try {
                        checkParentingAge(parent1, person);
                        parent1.adopt(person);
                    } catch (ParentingAgeException e) {
                        System.out.println("Błąd: " + e.getMessage());
                        System.out.println("Czy na pewno dodajemy? (y / n)");
                        Scanner scanner = new Scanner(System.in);
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("y")) {
                            parent1.adopt(person);
                        }
                    }

                    try {
                        checkParentingAge(parent2, person);
                        parent2.adopt(person);
                    } catch (ParentingAgeException e) {
                        System.out.println("Błąd: " + e.getMessage());
                        System.out.println("Czy na pewno dodajemy (y / n)?");
                        Scanner scanner = new Scanner(System.in);
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("y")) {
                            parent2.adopt(person);
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku !!!!!!!!!");
            throw new RuntimeException(e);
        } catch (NegativeLifespanException | AmbiguousPersonException e) {
            throw new RuntimeException(e);
        }
        return ppl;
    }

    private static void checkParentingAge(Person parent, Person child) throws ParentingAgeException {
        if (parent.getBirthDate().plusYears(15).isAfter(child.getBirthDate())) {
            throw new ParentingAgeException("Rodzic jest młodszy niż 15 lat w chwili narodzin dziecka.");
        }
        if (parent.getDeathDate() != null && parent.getDeathDate().isBefore(child.getBirthDate())) {
            throw new ParentingAgeException("Rodzic nie żyje w chwili narodzin dziecka.");
        }
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public static void toBinaryFile(List<Person> ppl, String path) throws FileNotFoundException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))){
            outputStream.writeObject(ppl);
        } catch (IOException e) {
            System.out.println("Błąd przy zapisywaniu do pliku w metodzie \ntoBinaryFile\n" + e.getMessage());
        }
    }

    public static List<Person> fromBinaryFile(String path) throws FileNotFoundException {
        List<Person> ppl = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))){
            ppl = (List<Person>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Błąd odczytu pliku binarnego w metodzie \nfromBinaryFile\n" + e.getMessage());
        }
        return ppl;
    }



//    Zad.2

//    public static List<Person> fromCsv(String path) throws FileNotFoundException {
//        List<Person> ppl = new ArrayList<>();
//        String[] parts = path.split(",");
//        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//            String line;
//            while ((line = br.readLine()) != null){
//                Person person = Person.fromCsvLine(line);
//                ppl.add(person);
//            }
//        } catch (IOException e) {
//            System.out.println("Błąd odczytu pliku !!!!!!!!!");
//            throw new RuntimeException(e);
//        } catch (NegativeLifespanException e) {
//            throw new RuntimeException(e);
//        }
//        return ppl;
//    }



//    Collections.sort(al, Collections.reverseOrder());

}

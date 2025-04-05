import java.util.*;

public class Family {
    private Map<String, List<Person>> familyMembers;

    public Family() {
        this.familyMembers = new HashMap<>();
    }

//    public void add(Person person) {
//        String key = person.getName() + " " + person.getSurname();
//        this.familyMembers.put(key, person);
//    }

//    public void add(Person... persons) {
//        for (Person p : persons) {
//            String key = p.getName() + " " + p.getSurname();
//            this.familyMembers.put(key, p);
//        }
//    }

    public void add(Person... persons) {
        for (Person p : persons) {
            String key = p.getName() + " " + p.getSurname();
            this.familyMembers.putIfAbsent(key, new ArrayList<>());
            this.familyMembers.get(key).add(p);
        }
    }

    public Person[] get(String key) {
        List<Person> people = this.familyMembers.get(key);
        if (people == null || people.isEmpty()) {
            return null;
        }
//        Collections.sort(people);
        people.sort(Comparator.reverseOrder());
        return people.toArray(new Person[0]);
    }


}

# Klasa

Klasa jest to szablon, receptura za pomocą której tworzymy obiekty.

Definiuje ona pola, czy też atrybuty danej klasy, które są jej cechami, jak np.rasa kota, moc samochodu, smak lodów.
Do tworzenia klasy wykorzystujemy specjalną metodę zwaną konstruktorem. Jest to dokładny zbior instrukcji potrzebny żeby utworzyć dany obiekt klasy, czyli mamy klasę ciasto, wiemy że składa ona się z danych składnników (pól), a w konstruktorze mamy konkretną receptórę tworzącą to ciasto.

## Obiekty
Obiekty to konkretne, gotowe do użytku instancje danej klasy utworzone za pomocą konstruktora, lub innych metod wytwórczych. Do konkretnego obiektu klay, wewnątrz jej kodu odwołujemy się za pomocą słowa kluczowego **this**. Jest ono niezbędne gdy argument metody, oraz pole klasy mają taką samą nazwę.

## Metody
Klasa zawiera też swoje metody, czyli należące do niej zachowania, jak szcekanie psa, uruchamianie samochodu, skakanie kota. Specjalnym typem metod są akcesory i mutatory, służące do zwracania lub zmieniania pól klasy. Sa one niezbędne gdy mamy do czynienia z enkapsulacją.

W zależności od tego czy są statyczne czy nie do ich wywołania potrzebujemy konkretnego obiektu, lub samej klasy.

Metody statyczne możemy wywoływać na klasie.

## Enkapsulacja

Jest to sposób ograniczania dostępu do pól i metod klasy. Mamy 3 poziomy / typy dostepu:


1.   Public - dostęp do danego pola / metody nie jest niczym ograniczony.
2.   Private - do danego pola mamy dost.ep tylko wewnątrz danej klasy, lub za pomocą gettarów i setterów.
3.  Protected - to samo co private, tylko do pola mają jeszcze dotęp klasy ddziedziczące po naszej klasie.



Klasę definiujemy w nast. sposób:

```java
public class RaceCar {
    // Pola klasy
    private String drivetrain;
    private int power;
    private float downforce;

    // Konstruktor
    public RaceCar(String drivetrain, int power, float downforce) {
        this.drivetrain = drivetrain;
        this.power = power;
        this.downforce = downforce;
    }

    // Akcesory (Gettery)
    public String getDrivetrain() {
        return drivetrain;
    }

    public int getPower() {
        return power;
    }

    public float getDownforce() {
        return downforce;
    }

    // Mutatoru (Settery)
    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDownforce(float downforce) {
        this.downforce = downforce;
    }

    public void refuel(float amount) {
        if (amount <= 0) {
            System.out.println("Wartość musi być dodatnia.");
            return;
        }

        currentFuelLevel += amount;
        if (currentFuelLevel > fuelTankCapacity) {
            currentFuelLevel = fuelTankCapacity;
            System.out.println("Bak jest pełny.");
        } else {
            System.out.println("Zatankowano " + amount + " litrów. Obecne paliwo w baku: " + currentFuelLevel + " litrów.");
        }
    }

    // Metoda statyczna klasy
    public static String startUp() {
        return "Uruchamiamy wyścigówkę...\n"
             + "1. Włączamy pompę paliwa\n"
             + "2. Podnosimy ciśnienie przewodów paliwowych\n"
             + "3. Włączamy ECU\n"
             + "4. Odpalamy silnik\n";
    }
}

```

```java
public class Main {
    public static void main(String[] args) {
        // Tworzymy obiekt klasy
        RaceCar myCar = new RaceCar("AWD", 650, 300.0f, 100.0f);

        // Dobieramy się do pól klasy za pomocą akcesorów
        System.out.println("Napęd: " + myCar.getDrivetrain());
        System.out.println("Moc: " + myCar.getPower() + " hp");
        System.out.println("Docisk: " + myCar.getDownforce() + " kg");

        // Modyfikujemy pola za pomocą mutatorów
        myCar.setPower(800);
        myCar.setDownforce(350.0f);

        System.out.println("\nNowa Moc: " + myCar.getPower() + " hp");
        System.out.println("Nowy Docisk: " + myCar.getDownforce() + " kg");

        // wywołujemy metodę statyczną klasy
        System.out.println("\n" + RaceCar.startUp());

        // wywołujemy metody instancji na danym obiekcie klasy
        myCar.refuel(30.0f);
        myCar.refuel(80.0f);
    }
}

```

# Dziedziczenie

W programowianiu obiektowy dziedziczenie jest to zabieg w którym jedna klasa jest pochodną poprzedniej poprzedniej klasy, i przejmuje / dziedziczący po niej pola i metody.

Dziedziczone metody można wywoływać w klasie pochodznej, za pomocą **super()**. Metody te można też nadpisywać kompletnie za pomocą **@Override**.
Aby klasa dziedziczyła po innej wykorzystujemy **extends**.

W javie jeden obiekt może dziedziczyć po wielu obiektach wieć multiple iheritence jest możliwe.

**Car.java**
```java
public class Car {
    protected String drivetrain;
    protected int power;
    protected int doorNumber;

    public Car(String drivetrain, int power, int doorNumber) {
        this.drivetrain = drivetrain;
        this.power = power;
        this.doorNumber = doorNumber;
    }

    public String startUp() {
        return "Uruchamiamy normalny sahmochód...";
    }

    // Akcesory
    public String getDrivetrain() {
        return drivetrain;
    }

    public int getPower() {
        return power;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    // Mutatory
    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }
}

```
**RaceCar.java**
```java
public class RaceCar extends Car {
    private float downforce;
    private float fuelTankCapacity;
    private float currentFuelLevel;

    public RaceCar(String drivetrain, int power, float downforce, float fuelTankCapacity) {
        // Super - wywołanie kosntruktora z klasy matczynej
        super(drivetrain, power, 2); // Wyścigówki mają 2 drzwi
        this.downforce = downforce;
        this.fuelTankCapacity = fuelTankCapacity;
        this.currentFuelLevel = 0.0f;
    }

    public float getDownforce() {
        return downforce;
    }

    public float getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public float getCurrentFuelLevel() {
        return currentFuelLevel;
    }

    public void setDownforce(float downforce) {
        this.downforce = downforce;
    }

    public void setFuelTankCapacity(float fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public void refuel(float amount) {
        if (amount <= 0) {
            System.out.println("Wartość musi być dodatnia.");
            return;
        }

        currentFuelLevel += amount;
        if (currentFuelLevel > fuelTankCapacity) {
            currentFuelLevel = fuelTankCapacity;
            System.out.println("Bak jest pełny.");
        } else {
            System.out.println("Zatankowano " + amount + " litrów. Obecne paliwo w baku: " + currentFuelLevel + " litrów.");
        }
    }

    // Nadpisana metoda
    @Override
    public String startUp() {
        return "Uruchamiamy wyścigówkę...\n"
             + "1. Włączamy pompę paliwa\n"
             + "2. Podnosimy ciśnienie przewodów paliwowych\n"
             + "3. Włączamy ECU\n"
             + "4. Odpalamy silnik\n";
    }
}


```

# Klasy Abstrakcyjne

Z klas abstrakcyjnych **nie możemy tworzyć obiektów**, oraz zawierają one metody abstrakcyjne. Metody te nie mają żadnej implmentacji a wszystkie klasy dziedziczące po klasie metody implementować. Klasy same w sobie mogą zawierać pola i normalne zaimplementowane metody.

Abstract Car

```java
public abstract class Car {
    protected String drivetrain;
    protected int power;
    protected int doorNumber;

    public Car(String drivetrain, int power, int doorNumber) {
        this.drivetrain = drivetrain;
        this.power = power;
        this.doorNumber = doorNumber;
    }

    public abstract String startUp();

    public String getDrivetrain() {
        return drivetrain;
    }

    public int getPower() {
        return power;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }
}


```
RaceCar

```java
public class RaceCar extends Car {
    private float downforce;
    private float fuelTankCapacity;
    private float currentFuelLevel;

    public RaceCar(String drivetrain, int power, float downforce, float fuelTankCapacity) {
        super(drivetrain, power, 2);
        this.downforce = downforce;
        this.fuelTankCapacity = fuelTankCapacity;
        this.currentFuelLevel = 0.0f;
    }

    @Override
    public String startUp() {
        return "Uruchamiamy wyścigówkę...\n"
             + "1. Włączamy pompę paliwa\n"
             + "2. Podnosimy ciśnienie przewodów paliwowych\n"
             + "3. Włączamy ECU\n"
             + "4. Odpalamy silnik\n";
    }

    public void refuel(float amount) {
        if (amount <= 0) {
            System.out.println("Wartość musi być dodatnia.");
            return;
        }

        currentFuelLevel += amount;
        if (currentFuelLevel > fuelTankCapacity) {
            currentFuelLevel = fuelTankCapacity;
            System.out.println("Bak jest pełny.");
        } else {
            System.out.println("Zatankowano " + amount + " litrów. Obecne paliwo w baku: " + currentFuelLevel + " litrów.");
        }
    }

    public float getDownforce() {
        return downforce;
    }

    public float getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public float getCurrentFuelLevel() {
        return currentFuelLevel;
    }

    public void setDownforce(float downforce) {
        this.downforce = downforce;
    }

    public void setFuelTankCapacity(float fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

}

```

RoadCar

```java
public class RoadCar extends Car {
    private float fuelEfficiency;

    public RoadCar(String drivetrain, int power, int doorNumber, float fuelEfficiency) {
        super(drivetrain, power, doorNumber);
        this.fuelEfficiency = fuelEfficiency;
    }

    public float getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(float fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }


    // Implementujemy metodę abstrakcyjną
    @Override
    public String startUp() {
        return "Odpalamy normalny samochód...\n"
             + "1. Przekręcamy kluczyk\n"
             + "Możemy jechać.";
    }
}
```


# Kontenery
Czy też Collections pozwalająn na grupowanie obiektów danej klasy, jak Integer, String, RaceCar w jedną zmienną. W Javie mamy wiele typów Kolekcji:

https://www.geeksforgeeks.org/collections-in-java-2/

ale najistotniejszymi są:

## ArrayList / Lista
Coś jak niekończąca się tablica. Elementy są w niej uporządkowane, czyli zawierają indeksy, oraz dopuszczane są duplikaty

```java
List<RaceCar> raceCars = new ArrayList<>();
raceCars.add(new RaceCar("RWD", 700, 350.0f, 80.0f));
raceCars.add(new RaceCar("AWD", 850, 400.0f, 90.0f));
```

## Set
Nieuporządkowana lista, zawierająca tylko unikalne elementy, czyli niedopuszczalne są duplikaty

```java
Set<String> raceCarBrands = new HashSet<>();
raceCarBrands.add("Ferrari");
raceCarBrands.add("Aston Martin");
raceCarBrands.add("Ford");
```

## Map (HashMap)

Składa się z par key - value. Własnoręcznie definiujemy klucz, i działa on jak swojego rodzaju 'customowy' index. Jeśli chodzi to klucze to niedozwolone są duplikaty.

```java
Map<Integer, RaceCar> raceCarNumbers = new HashMap<>();
raceCarNumbers.put(44, new RaceCar("RWD", 900, 420.0f, 100.0f));
raceCarNumbers.put(33, new RaceCar("AWD", 850, 410.0f, 95.0f));

RaceCar car44 = raceCarNumbers.get(44);
```

## Kolejka, Queue

Coś jak lista, tylko że nie mamy beżpośredniego dostępu do indeksów. Elementy są dodawane i wyjmowane wg. strategii FIFO (First In First Out). Czyli jak normalna kolejka np. w skelpie do mięsa.

```java
Queue<RaceCar> pitStopQueue = new LinkedList<>();
pitStopQueue.add(new RaceCar("RWD", 800, 370.0f, 85.0f));
pitStopQueue.add(new RaceCar("AWD", 850, 390.0f, 88.0f));
```
## Stos, Stack

Identyczne do kolejki, tylko stratego dodawania i wyjmowania jest inna. Tym razem mamy do czynienia z LIFO (Last In First Out), czyli jak stos talerzy / ksiąrzek. Dodajemy na górę i zdejmujemy z góry.

```java
Stack<RaceCar> stack = new Stack<>();
stack.push(new RaceCar("RWD", 750, 360.0f, 80.0f));
stack.push(new RaceCar("AWD", 870, 400.0f, 90.0f));
```


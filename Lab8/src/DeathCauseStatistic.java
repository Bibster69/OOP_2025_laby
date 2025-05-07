public class DeathCauseStatistic {
    private String icd10Code;
    private int[] deathsByAgeGroup;

    private DeathCauseStatistic(String icd10Code, int[] deathsByAgeGroup) {
        this.icd10Code = icd10Code;
        this.deathsByAgeGroup = deathsByAgeGroup;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public static DeathCauseStatistic fromCsvLine(String line) {
        String[] tokens = line.trim().split(",");

        String icd10Code = tokens[0];
        int[] deathsByAgeGroup = new int[tokens.length - 1];
        for (int i = 1; i < tokens.length; i++) {
            if (tokens[i].equals("-")) {
                deathsByAgeGroup[i - 1] = 0;
            } else {
                deathsByAgeGroup[i - 1] = Integer.parseInt(tokens[i]);
            }
        }
        return new DeathCauseStatistic(icd10Code, deathsByAgeGroup);
    }

    public int getDeathsByAgeGroup(int ageGroupIndex) {
        if (ageGroupIndex < 0 || ageGroupIndex > this.deathsByAgeGroup.length) {
            throw new IndexOutOfBoundsException("Nieprawido indeks grupy wiekowej");
        }

        return this.deathsByAgeGroup[ageGroupIndex];
    }

    public AgeBracketDeaths getAgeBracket(int age) {
        for (int i = 0; i < AGE_BRACKETS.length; i++) {
            int[] brackets = AGE_BRACKETS[i];
            if (age >= brackets[0] && age <= brackets[1]) {
                return new AgeBracketDeaths(brackets[0], brackets[1], deathsByAgeGroup[i]);
            }
        }
        return null;
    }

    public class AgeBracketDeaths {
        public final int young;
        public final int old;
        public final int deathCount;

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }


        @Override
        public String toString() {
            return String.format("Wiek: %d-%d, zgony: %d", young, old, deathCount);
        }

    }

    private static final int[][] AGE_BRACKETS = {
            {0, 4}, {5, 9}, {10, 14}, {15, 19}, {20, 24},
            {25, 29}, {30, 34}, {35, 39}, {40, 44}, {45, 49},
            {50, 54}, {55, 59}, {60, 64}, {65, 69}, {70, 74},
            {75, 79}, {80, 84}, {85, 89}, {90, 94}, {95, 200}
    };
}

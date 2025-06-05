import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Election election = new Election();
        election.populate("resources/kandydaci.txt");
        System.out.println(election.getCandidates());

        System.out.println(election.getFirstTurn().summarize());

//
//        Election election2020 = new Election();
//        election2020.populate("resources/1.csv");
//        //System.out.println(election2020.getFirstTurn().winner());
//        //Result result2020First = election2020.getFirstTurn().summarize();
        Vote result2020First = election.getFirstTurn().summarize(List.of("lubelskie", "Lublin", "m. Lublin"));
        System.out.println(result2020First);
        System.out.println(election.getWinner());

//        Map<String, Vote> localResults = new HashMap<>();
//        for (String voivodeship : VoivodeshipMap.voivodeships()) {
//            Vote localResult = election2020.getSecondTurn().summarize((List.of(voivodeship)));
//            localResults.put(voivodeship, localResult);
//        }



    }
}
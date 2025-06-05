import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ElectionTurn {
    private List<Candidate> candidates;
    private List<Vote> votes;

    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void populate(String path) {
        this.votes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // nagłówek
            while ((line = br.readLine()) != null) {
                Vote vote =  Vote.fromCsvLine(line, this.candidates);
                this.votes.add(vote);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Vote summarize() {
        return Vote.summarize(this.votes, null);
    }

    public Vote summarize(List<String> location) {
        List<Vote> filteredResults = Vote.filterByLocation(location, this.votes);
        return Vote.summarize(this.votes, location);
    }

    public Candidate winner() {
        Vote summarized = Vote.summarize(this.votes, null);
        for (int i = 0; i < candidates.size(); i++) {
            Candidate candidate = candidates.get(i);
            assert summarized != null;
            if (summarized.percentage(candidate) > 50.0f) {
                return candidate;
            }
        }
        throw new NoWinnerException("Brak wygranego");
    }

    public List<Candidate> runoff() {
        Vote summarized = Vote.summarize(this.votes, null);

        List<Candidate> sortedResults = new ArrayList<>(candidates);
        Collections.sort(sortedResults, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate c1, Candidate c2) {
                int votes1 = summarized.votes(c1);
                int votes2 = summarized.votes(c2);
                return Integer.compare(votes2, votes1);
            }
        });

        if (sortedResults.size() < 2) {
            return Collections.emptyList();
        }

        List<Candidate> topTwo = new ArrayList<>();
        topTwo.add(sortedResults.get(0));
        topTwo.add(sortedResults.get(1));
        return topTwo;
    }

}

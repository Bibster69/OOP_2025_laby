import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Election {
    private List<Candidate> candidates;
    ElectionTurn firstTurn;
    ElectionTurn secondTurn;
    Candidate winner;

    public List<Candidate> getCandidates() {
        return new ArrayList<Candidate>(this.candidates);
    }

    public Election() {
        this.candidates = new ArrayList<>();
        this.firstTurn = new ElectionTurn(this.candidates);
        this.secondTurn = null;
    }

    private void populateCandidates(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                this.candidates.add(new Candidate(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void populate(String path) {
        populateCandidates(path);
        this.firstTurn.populate("resources/1.csv");
        try {
            this.winner = this.firstTurn.winner();
        } catch (NoWinnerException e) {
            List<Candidate> runoffCandidates = this.firstTurn.runoff();
            this.secondTurn = new ElectionTurn(runoffCandidates);
            this.secondTurn.populate("resources/2.csv");
            try {
                this.winner = this.secondTurn.winner();
            } catch (NoWinnerException ee) {
                throw new NoWinnerException("Brak zwycięscy");
            }
        }
    }

    public ElectionTurn getFirstTurn() {
        return this.firstTurn;
    }

    public ElectionTurn getSecondTurn() {
        return secondTurn;
    }

    public Candidate getWinner() {
        return winner;
    }
}

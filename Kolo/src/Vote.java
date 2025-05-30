import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vote {
    private HashMap<Candidate, Integer> votesForCandidate;
    private List<String> location;
    private Integer sum;

    public static Vote fromCsvLine(String line, List<Candidate> candidates) {

        String[] tokens = line.split(",");

        Vote result = new Vote();

        result.location = new ArrayList<String>();
        result.location.add(tokens[2]); // Województwo
        result.location.add(tokens[1]); // Powiat
        result.location.add(tokens[0]); // Gmina

        result.votesForCandidate = new HashMap<Candidate, Integer>();
        for (int i = 0; i < candidates.size(); i++) {
            Candidate candidate = candidates.get(i);
            String voteString = tokens[i + 3];
            int votes = Integer.parseInt(voteString);
            result.votesForCandidate.put(candidate, votes);
        }

        return result;
    }

    public static Vote summarize(List<Vote> votes, List<String> location) {
        if (votes.isEmpty()) {
            System.out.println("Pusta lista");
            return null;
        }

        Vote result = new Vote();
        if (location == null) {
            result.location = new ArrayList<>();
        }
        else {
            result.location = location;
        }
        result.votesForCandidate = new HashMap<>();

        Vote firstVote = votes.get(0);

        for (Candidate candidate : firstVote.votesForCandidate.keySet()) {
            int sumOfVotes = 0;
            for(int i = 0; i < votes.size(); i++) {
                Vote vote = votes.get(i);
                sumOfVotes += vote.votesForCandidate.get(candidate);
            }
            result.votesForCandidate.put(candidate, sumOfVotes);
        }
        return result;
    }

    public int votes(Candidate candidate) {
        return this.votesForCandidate.get(candidate);
    }

    public int sumOfVotes(){
        if (this.sum == null){
            this.sum = 0;
            for (Integer candidateVotes : this.votesForCandidate.values()) {
                sum += candidateVotes;
            }
        }
        return this.sum;
    }

    public Float percentage(Candidate candidate) {
        if (this.votesForCandidate.containsKey(candidate)) {
            return (float) this.votesForCandidate.get(candidate) / sumOfVotes() * 100.0f;
        } else {
            return null;
        }
    }

    public static List<Vote> filterByLocation(List<String> locations, List<Vote> votes) {
        int locationsSize = locations.size();

        List<Vote> filtered = new ArrayList<Vote>();

        for (Vote vote : votes) {
            boolean matches = true;
            for(int i = 0; i < locationsSize; i++) {
                if (!vote.location.get(i).equals(locations.get(i))) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                filtered.add(vote);
            }
        }
        return filtered;
    }

    @Override
    public String toString() {
        String locationStr = "";
        for (int i = 0; i < this.location.size(); i++) {
            if (i > 0) {
                locationStr += " ";
            }
            locationStr += this.location.get(i);
        }

        String result = locationStr + "\n";

        for (Candidate candidate : this.votesForCandidate.keySet()) {
            Float percent = percentage(candidate);
            result += String.format("%s %.2f%%\n", candidate.name(), percent);
        }

        return result.trim();
    }

}

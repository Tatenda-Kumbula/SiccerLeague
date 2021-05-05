
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SoccerLeague {

    public static Map<String, Results> teamResults = new HashMap<>();

    private static class Results {
        private int wins = 0;
        private int looses = 0;

        public void addWin(int inc) {
            wins = wins + inc;
        }

        public void addLost() {
            looses++;
        }

        @Override
        public String toString() {
            return "" + wins ;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("AnimalPremierMatches.txt"));

        String line;

        while ((line = br.readLine()) != null) { //to check EOF

            String[] data = line.split(" ");

            // Get the team name per line
            Results team1 = teamResults.getOrDefault(data[0], new Results());
            Results team2 = teamResults.getOrDefault(data[2], new Results());

            if (Integer.valueOf(data[1]) > Integer.valueOf(data[3])) {
                team1.addWin(3);
                team2.addLost();
            } else if (Integer.valueOf(data[1]) == Integer.valueOf(data[3])) {
                team1.addWin(1);
                team2.addWin(1);
            } else {
                team1.addLost();
                team2.addWin(3);
            }

            teamResults.put(data[0], team1);
            teamResults.put(data[2], team2);

        } // End of while

        SoccerLeague soccerLeague = new SoccerLeague();

        soccerLeague.sortAgain();
    }

    public void printMap(Map<String, Results> teamResults) {
        int i = 0;

        for (String string : teamResults.keySet()) {
            i++;
            Results teamRes = teamResults.get(string);
            System.out.println(i + ". " + string + "\n" + teamRes);
        }
    }

    void sortByValue(boolean order) {
        List<Map.Entry<String, Results>> list = new LinkedList<Map.Entry<String, Results>>(teamResults.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Results>>() {
            @Override
            public int compare(Map.Entry<String, Results> o1, Map.Entry<String, Results> o2) {
                if (order) {
                    return String.valueOf(o1.getValue()).compareTo(String.valueOf(o2.getValue()));
                } else {
                    return String.valueOf(o2.getValue()).compareTo(String.valueOf(o1.getValue()));
                }
            }
        });

        Map<String, Results> sortedResults = new LinkedHashMap<String, Results>();

        for (Map.Entry<String, Results> entry : list) {
            sortedResults.put(entry.getKey(), entry.getValue());
        }

        printMap(sortedResults);
    }


    public void sortAgain() {
        SortedSet<Map.Entry<String, Results>> sortedSet = new TreeSet<>(new Comparator<Map.Entry<String, Results>>() {
            @Override
            public int compare(Map.Entry<String, Results> o1, Map.Entry<String, Results> o2) {
                int res = String.valueOf(o1.getValue()).compareTo(String.valueOf(o2.getValue()));
                if (res == 0) {
                    return String.valueOf(o1.getKey()).compareTo(String.valueOf(o2.getKey()));
                } else {
                    return res * -1;
                }
            }
        });

        sortedSet.addAll(teamResults.entrySet());

        List<String> list = new ArrayList<>();

        for (Map.Entry<String, Results> e : sortedSet) {
            list.add(e.getKey());
        }

        int i = 0;

        for(Map.Entry<String, Results> s : sortedSet) {
            i++;
            System.out.println(i + ". " + s.getKey() + "\n" + s.getValue() );
        }
    }
}
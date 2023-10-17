package Application.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
public class TeamGeneratorController {

    @GetMapping("/teamsOld/{n}")
    public ArrayList<String> generateTeams(@PathVariable int n) {
        ArrayList<Integer> people = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }
        Collections.shuffle(people); // losowe przetasowanie listy osób

        ArrayList<String> teams = new ArrayList<String>();
        for (int i = 0; i < n-1; i++) {
            ArrayList<Integer> team1 = new ArrayList<Integer>();
            ArrayList<Integer> team2 = new ArrayList<Integer>();
            for (int j = 0; j < n/2; j++) {
                int person1 = people.get(j);
                int person2 = people.get(n-1-j);
                team1.add(person1);
                team2.add(person2);
            }
            if(n%2!=0) {
                int oddPerson = people.get(n / 2);
                int randomTeam = new Random().nextInt(2); // losowy wybór drużyny do dodania osoby nieparzystej
                if (randomTeam == 0) {
                    team1.add(oddPerson);
                } else {
                    team2.add(oddPerson);
                }
            }
            teams.add("Runda " + (i+1) + ": Team 1 - " + team1 + " vs Team 2 - " + team2);
            Collections.rotate(people.subList(1, people.size()), 1); // przesunięcie listy o jedną pozycję w lewo

        }

        return teams;
    }

    @GetMapping("/teams/{nInput}")
    public List<List<List<Integer>>> generateCombinations(@PathVariable int nInput)  {
        int lastIndex = 0;
        if(nInput%2!=0) {
            nInput=nInput+1;
            lastIndex=nInput;
        }
        int[] input = new int[nInput];
        for (int i = 0; i < nInput; i++) {
            input[i] = i+1;
        }
        List<List<Integer>> result = new ArrayList<>();
//        int test=0;
        int n = input.length;
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> combination = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    combination.add(input[j]);
                }
            }
//            result.add(combination);
            if(combination.size() == input.length/2 && lastIndex == 0) {

                result.add(combination);
            } else if (combination.size() == input.length/2 && lastIndex!= 0) {
//                System.out.println(combination.size());
//                System.out.println(combination);
//                if(test == 0) {
//                    combination.add(lastIndex);
//                    test++;
                System.out.println(lastIndex);
                combination.remove(Integer.valueOf(lastIndex));
                System.out.println(combination);
                result.add(combination);
//                }else {
//                    result.add(combination);
//                    test=0;
//                }
            }
            else{
//                System.out.println(combination.size());
//                System.out.println(combination);
            }
        }

        List<List<Integer>> firstHalf = new ArrayList<>(result.subList(0, result.size()/2));
        List<List<Integer>> secondHalf = new ArrayList<>(result.subList(result.size()/2, result.size()));
        Collections.reverse(secondHalf);
        System.out.println(firstHalf);
        System.out.println(secondHalf);
        List<List<List<Integer>>> combined = new ArrayList<>();
        combined.add(firstHalf);
        combined.add(secondHalf);

        System.out.println(combined);
        return combined;
    }

    @GetMapping("/teams/{tableNames}")
    List<List<List<String>>> combinationsToString(@PathVariable String[] tableNames) {
        int nInput = tableNames.length;
        List<List<List<Integer>>> combinations = generateCombinations(nInput);
        List<String> wordList = List.of(tableNames);
        System.out.println(wordList);
        System.out.println(combinations);

        List<List<List<String>>> stringList = new ArrayList<>();

        for (List<List<Integer>> outerList : combinations) {
            List<List<String>> subResult = new ArrayList<>();
            for (List<Integer> innerList : outerList) {
                List<String> innerSubResult = new ArrayList<>();
                for (Integer i : innerList) {
                    innerSubResult.add(tableNames[i-1]); // minus 1 because arrays are 0-indexed
                }
                subResult.add(innerSubResult);
            }
            stringList.add(subResult);
        }
        return stringList;
    }

}
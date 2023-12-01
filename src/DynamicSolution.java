import java.util.*;

public class DynamicSolution {
    private HashMap<String, ArrayList> wordFamilies = new HashMap<>();
    private ArrayList<String> remainingWordList = new ArrayList<>();
    private String partialSolution;

    private int solutionLength;

    public int getSolutionLength(){
        return solutionLength;
    }

    public String getPartialSolution(){
        return partialSolution;
    }

    public HashMap<String, ArrayList> getWordFamilies(){
        return wordFamilies;
    }

    public ArrayList<String> getRemainingWordList(){
        return remainingWordList;
    }

    public DynamicSolution(ArrayList<String> wordList, int minLengthInWordList, int maxLengthInWordList){
        Random rand = new Random();

        if (minLengthInWordList == maxLengthInWordList){
            solutionLength = minLengthInWordList;
        } else {
            solutionLength = rand.nextInt(maxLengthInWordList - minLengthInWordList) + minLengthInWordList;
        }

        StringBuilder noGuess = new StringBuilder();
        for (int i = 0; i < solutionLength; i++){
            noGuess.append("-");
        }

        partialSolution = noGuess.toString();

        wordFamilies.put(partialSolution, wordList);

        for (String word : wordList) {
            if (word.length() == solutionLength) {
                remainingWordList.add(word);
            }
        }

    }

    public boolean isSolved(){
        return !partialSolution.contains("-");
    }

    public void printProgress() {
        System.out.println("Current progress: " + partialSolution);
    }

    public void printVictory() {
        System.out.printf("Congrats! The word was %s.", partialSolution);
    }

    public boolean addGuess(String newGuess) {
        boolean guessCorrect = false;
        updateWordFamilies(newGuess);

        if (updatePartialSolution()){
            updateRemainingWordList();
            guessCorrect = true;
            System.out.println("Good job, you made a correct guess! Please continue.\n");
        } else {
            System.out.println("Oops, this is an incorrect guess! Please try again.\n");
        }

        return guessCorrect;
    }

    public void updateWordFamilies(String newGuess){
        HashMap<String, ArrayList> newWordFamilies = new HashMap<>();

        for (String word : remainingWordList){
            StringBuilder wordFamilyKeyBuilder = new StringBuilder();

            for (int i = 0; i < solutionLength; i++){
                if (!Character.toString(partialSolution.charAt(i)).equals("-")){
                    wordFamilyKeyBuilder.append(partialSolution.charAt(i));
                } else {
                    if (Character.toString(word.charAt(i)).equals(newGuess)){
                        wordFamilyKeyBuilder.append(newGuess);
                    } else {
                        wordFamilyKeyBuilder.append("-");
                    }
                }
            }

            String wordFamilyKey = wordFamilyKeyBuilder.toString();

            if (newWordFamilies.containsKey(wordFamilyKey)){
                ArrayList<String> wordFamilyValues = newWordFamilies.get(wordFamilyKey);
                wordFamilyValues.add(word);
            } else {
                ArrayList<String> newWordFamily = new ArrayList<>();
                newWordFamily.add(word);
                newWordFamilies.put(wordFamilyKey, newWordFamily);
            }
        }

        wordFamilies = newWordFamilies;
    }

    public boolean updatePartialSolution(){
        boolean partialSolutionUpdated = false;
        int numberOfWordsInLargestWordFamily = 0;
        String updatedPartialSolution = partialSolution;

        for (String wordFamilyKey : wordFamilies.keySet()){
            if (wordFamilies.get(wordFamilyKey).size() > numberOfWordsInLargestWordFamily){
                numberOfWordsInLargestWordFamily = wordFamilies.get(wordFamilyKey).size();
                updatedPartialSolution = wordFamilyKey;
            }
        }

        if (!updatedPartialSolution.equals(partialSolution)){
            partialSolutionUpdated = true;
            partialSolution = updatedPartialSolution;
        }

        return partialSolutionUpdated;
    }

    public void updateRemainingWordList(){
        remainingWordList = wordFamilies.get(partialSolution);
    }
}

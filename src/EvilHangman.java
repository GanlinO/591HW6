import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class EvilHangman {
    private ArrayList<String> wordList = new ArrayList<>();

    private HashSet<Character> previousGuesses;
    private TreeSet<Character> incorrectGuesses;

    private DynamicSolution solution;

    private Scanner inputScanner;

    private int minLengthInWordList;
    private int maxLengthInWordList;

    public int getMinLengthInWordList(){
        return minLengthInWordList;
    }

    public int getMaxLengthInWordList(){
        return maxLengthInWordList;
    }

    public ArrayList<String> getWordList(){
        return wordList;
    }

    public EvilHangman(String filename) {
        try {
            minLengthInWordList = 100;
            maxLengthInWordList = 1;
            wordList = dictionaryToArrayList(filename);
        } catch (IOException e) {
            System.out.printf(
                    "Couldn't read from the file %s. Verify that you have it in the right place and try running again.", filename);
            e.printStackTrace();
            System.exit(0);
        }

        previousGuesses = new HashSet<>();
        incorrectGuesses = new TreeSet<>();

        solution = new DynamicSolution(wordList, minLengthInWordList, maxLengthInWordList);
        inputScanner = new Scanner(System.in);
    }

    public void start() {
        while (!solution.isSolved()) {
            char newGuess = promptForGuess();
            recordGuess(newGuess);
        }
        solution.printVictory();
    }

    private char promptForGuess() {
        while (true) {
            solution.printProgress();
            System.out.println("Previous incorrect guesses: " + incorrectGuesses.toString());
            System.out.print("Please enter a lowercase alphabet: ");

            String input = inputScanner.next();

            if (input.length() != 1) {
                System.out.println("You entered more than one character!\n");
            } else if (!Character.isLowerCase(input.charAt(0))){
                System.out.println("You entered a non-alphabetic character!\n");
            } else if (previousGuesses.contains(input.charAt(0))) {
                System.out.println("You've already guessed that.\n");
            } else {
                return input.charAt(0);
            }
        }
    }

    private void recordGuess(char newGuess) {
        previousGuesses.add(newGuess);
        boolean isCorrect = solution.addGuess(String.valueOf(newGuess));

        if (!isCorrect) {
            incorrectGuesses.add(newGuess);
        }
    }
    private ArrayList<String> dictionaryToArrayList(String filename) throws IOException {
        FileInputStream file = new FileInputStream(filename);
        Scanner scnr = new Scanner(file);

        ArrayList<String> wordList = new ArrayList<>();

        while (scnr.hasNext()) {
            String next = scnr.next();
            wordList.add(next);

            if (next.length() > maxLengthInWordList){
                maxLengthInWordList = next.length();
            }

            if (next.length() < minLengthInWordList){
                minLengthInWordList = next.length();
            }
        }

        scnr.close();
        file.close();

        return wordList;
    }
}

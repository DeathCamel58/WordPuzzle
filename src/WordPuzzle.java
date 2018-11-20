
import java.io.*;
import java.util.*;
import java.lang.*;

public class WordPuzzle {
    private static String getWord(String filename) {
        try (FileInputStream fs = new FileInputStream(filename)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));

            // Store every line at a different index in array
            ArrayList<String> array = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                array.add(line);
            }

            // Generate a random line number
            Random rand = new Random();
            int randomIndex = rand.nextInt(array.size());

            // Return a random line
            return array.get(randomIndex);
        }
        catch (IOException ex) {
            return ex.toString();
        }
    }

    // Prints the current state of the random string
    private static void PrintCurrentState(String string) {
        StringBuilder numbers = new StringBuilder("0");
        String bar = "----------";

        // Because we want to count the numbers properly, and not hardcode in a length of word, this is necessary
        for (int i = 1; i < string.length(); i++) {
            numbers.append(i);
        }

        // Actually print now that the variables are set up
        System.out.println(bar);
        System.out.println(numbers);
        System.out.println(string);
        System.out.println(bar);
    }

    // Shuffle the letters of the word
    private static String RandomizeString(String string)
    {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        StringBuilder shuffled = new StringBuilder();
        for (String letter : letters) {
            shuffled.append(letter);
        }
        return shuffled.toString();
    }

    // Read a single character at a time, and build new string (with letters swapped)
    private static String SwapLetter(String string, int pos1, int pos2) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (i != pos1 && i != pos2) {
                newString.append(string.charAt(i));
            } else {
                if (i == pos1) {
                    newString.append(string.charAt(pos2));
                } else {
                    newString.append(string.charAt(pos1));
                }
            }
        }

        return newString.toString();
    }

    public static void main(String[] args) {
        String fileName = "/home/deathcamel57/Documents/dev/WordPuzzle/src/words.txt";

        // Get random word
        String word = getWord(fileName);

        // Randomize the string
        String randomString = RandomizeString(word);

        // Init keyboard
        Scanner keyboard;
        keyboard = new Scanner(System.in);

        boolean stopNow = false;
        int count = 0;
        while (!stopNow) {
            PrintCurrentState(randomString);

            System.out.println("Enter 1 to swap letters.");
            System.out.println("Enter 2 to solve the puzzle.");
            System.out.println("Enter 3 to quit the game.\n");
            String userOption = keyboard.nextLine();
            switch (Integer.parseInt(userOption)) {

                // If they want to swap letters
                case 1: System.out.println("Enter the indexes separated by a space.");
                    String userGuess = keyboard.nextLine();
                    String[] positions = userGuess.split(" ");
                    randomString = SwapLetter(randomString, Integer.valueOf(positions[0]), Integer.valueOf(positions[1]));
                    System.out.print("\n");
                    break;

                // If they want to solve the puzzle
                case 2: System.out.println("Please enter the solved word");
                    userOption = keyboard.nextLine();

                    // Check if they were correct
                    if (userOption.equals(word)) {
                        System.out.println("\nCongratulations! You solved the secret in " + count + " steps.");
                        stopNow = true;
                    } else {
                        System.out.println("\nWrong answer! Please try to solve again!");
                    }
                    break;

                // If they want to quit
                case 3: stopNow = true;
                    break;

                // If they typed something not allowed
                default: System.out.println("Invalid option... Try again.");
                    break;
            }

            // Increment the number of tries
            count ++;
        }

        System.out.print("\n");
    }
}
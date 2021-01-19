import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class TypingGame {

    private static String randomWord(int length) {
        Random random = new Random();
        StringBuilder word = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            word.append((char)('a' + random.nextInt(26)));
        }
        return word.toString();
    }

    public static void main(String[] args) {
        // Startup Prompt
        System.out.println("Hello! This game will help you practice your typing. A random sequence of characters will be generated.\n" +
                "Retype them as quickly as you can. If your input is correct, a new word will be generated.\nWhen " +
                "you make a typo, the game will end and your stats will be displayed. Have fun!");

        // Set game settings
        Scanner scanner = new Scanner(System.in);
        int minLen, maxLen;

        while(true){
            System.out.print("\nChoose a minimum character length for each word. Must be between 3 and 50: ");
            minLen = Integer.parseInt(scanner.nextLine());
            if (minLen < 3 || minLen > 50) {
                continue;
            }
            break;
        }

        while(true) {
            System.out.print("\nChoose a maximum character length for each word. Must be between 3 and 50: ");
            maxLen = Integer.parseInt(scanner.nextLine());
            if (maxLen < 3 || maxLen > 50) {
                continue;
            }
            break;
        }


        System.out.print("\nPress enter to begin the game. ");
        scanner.nextLine();

        // Create ArrayList and variables to store stats.
        long startTime, endTime, typingTime;
        double convertedTime;
        ArrayList<Double> listOfTimes = new ArrayList<>();
        int characterCount = 0;

        // Game start
        while(true) {
            // Randomly choose the size of the string that will be created, within the given bound.
            int chosenStringSize = ThreadLocalRandom.current().nextInt(minLen, maxLen + 1);

            // Create and print random string of that size.
            String gameWord = randomWord(chosenStringSize);
            System.out.println("Copy the word: " + gameWord);
            startTime = System.nanoTime();

            // Read user input.
            String userWord = scanner.nextLine();
            endTime = System.nanoTime();

            // Compare user's word and the generated word.
            Boolean matchTest = userWord.equals(gameWord);
            if (!matchTest) {
                break;
            }

            // If the typed word is a match, calculate time elapsed and store it.
            characterCount+= gameWord.length();
            typingTime = endTime - startTime;
            convertedTime = typingTime / 1_000_000_000.0;
            listOfTimes.add(convertedTime);
        }

        // Calculate stats and print closing message.
        Double averageTime = calculateAverage(listOfTimes);
        Double totalTime = calculateTotal(listOfTimes);
        double cpm = characterCount / (totalTime/60);

        System.out.println("Average time per word: " + round(averageTime, 2) + " seconds." + "\nCharacters Per Minute (CPM): " + round(cpm,0) + "\nThank you for playing!");
    }

    // Calculates average value in an ArrayList
    private static Double calculateAverage(ArrayList <Double> marks) {
        Double average = 0.0;
        if(!marks.isEmpty()) {
            for (Double mark : marks) {
                average += mark;
            }
            return average / marks.size();
        }
        return average;
    }

    // Calculates total value in an ArrayList
    private static Double calculateTotal(ArrayList <Double> marks) {
        Double sum = 0.0;
        if(!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }
        }
        return sum;
    }

    // Rounds a double to a desired decimal place.
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

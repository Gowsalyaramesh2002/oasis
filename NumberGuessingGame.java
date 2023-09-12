import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1; // Set your lower bound
        int upperBound = 100; // Set your upper bound
        int numberToGuess = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        int numberOfTries = 0;
        boolean hasGuessedCorrectly = false;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between " + lowerBound + " and " + upperBound + ".");

        while (!hasGuessedCorrectly) {
            System.out.print("Enter your guess: ");
            int userGuess = scanner.nextInt();
            numberOfTries++;

            if (userGuess < lowerBound || userGuess > upperBound) {
                System.out.println("Please enter a number between " + lowerBound + " and " + upperBound + ".");
            } else if (userGuess < numberToGuess) {
                System.out.println("Try higher!");
            } else if (userGuess > numberToGuess) {
                System.out.println("Try lower!");
            } else {
                hasGuessedCorrectly = true;
                System.out.println("Congratulations! You guessed the number " + numberToGuess + " correctly in " + numberOfTries + " tries.");
            }
        }

        scanner.close();
    }
}

package edu.brandeis.cosi103a.ip1;

import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int p1Score = 0;
        int p2Score = 0;
        System.out.println("Welcome to the dice rolling game! Two players take turns rolling a die and adding its value to their score.");
        System.out.println("Each player can choose to re-roll the die up to two times per turn. After 10 turns, the player with the most points wins. Good luck!\n");
        for (int turn = 1; turn <= 10; turn++) {
            System.out.println("Turn " + turn + " for Player 1:");
            p1Score += playerTurn(scanner, rand);
            System.out.println("Current point total: " + p1Score + "\n");
            System.out.println("Turn " + turn + " for Player 2:");
            p2Score += playerTurn(scanner, rand);
            System.out.println("Current point total: " + p2Score + "\n");
        }
        System.out.println("Final scores:");
        System.out.println("Player 1: " + p1Score);
        System.out.println("Player 2: " + p2Score);
        if (p1Score > p2Score) {
            System.out.println("Player 1 wins! Congratulations!");
        } else if (p2Score > p1Score) {
            System.out.println("Player 2 wins! Congratulations!");
        } else {
            System.out.println("It's a tie! Good game! :)");
        }
        scanner.close();
    }

    private static int playerTurn(Scanner scanner, Random rand) {
        int val = 0;
        int rolls = 0;
        while (rolls < 3) {
            int dieValue = rand.nextInt(6) + 1;
            System.out.println("Roll: " + dieValue);
            val = dieValue;
            if (rolls < 2) {
                System.out.print("Do you want to re-roll? (yes/no): ");
                String input = scanner.nextLine();
                if (!input.equalsIgnoreCase("yes")) {
                    break;
                }
            }
            rolls++;
        }
        System.out.println("Your points for this turn: " + val);
        return val;
    }
}

package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

public class AppTest {

    @Test
    public void testDeterministicGameNoRerolls() throws Exception {
        // prepare input: 20 "no" answers (10 turns per player, 2 players)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) sb.append("no\n");
        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        Scanner scanner = new Scanner(in);

        long seed = 12345L;
        Random randomForRun = new Random(seed);

        // compute expected sums by simulating the sequence of single rolls
        Random randomForExpected = new Random(seed);
        int[] rolls = new int[20];
        for (int i = 0; i < 20; i++) rolls[i] = randomForExpected.nextInt(6) + 1;

        int expectedP1 = 0;
        int expectedP2 = 0;
        for (int turn = 0; turn < 10; turn++) {
            expectedP1 += rolls[turn * 2];
            expectedP2 += rolls[turn * 2 + 1];
        }

        // access private App.playerTurn(Scanner, Random)
        Method playerTurn = App.class.getDeclaredMethod("playerTurn", Scanner.class, Random.class);
        playerTurn.setAccessible(true);

        int actualP1 = 0;
        int actualP2 = 0;

        for (int turn = 1; turn <= 10; turn++) {
            actualP1 += (Integer) playerTurn.invoke(null, scanner, randomForRun);
            actualP2 += (Integer) playerTurn.invoke(null, scanner, randomForRun);
        }

        scanner.close();

        assertEquals("Player 1 total should match expected", expectedP1, actualP1);
        assertEquals("Player 2 total should match expected", expectedP2, actualP2);
    }
}

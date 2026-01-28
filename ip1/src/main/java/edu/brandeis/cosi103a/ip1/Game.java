package edu.brandeis.cosi103a.ip1;

import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;
    private Supply cardSupply;
    private Random rand;

    public Game() {
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.cardSupply = new Supply();
        this.rand = new Random();
    }

    public void setup() {
        System.out.println("Setting up the game...\n");
        // Give each player starter deck
        for (int i = 0; i < 7; i++) {
            player1.addCardToDeck(cardSupply.buyCard("Bitcoin"));
            player2.addCardToDeck(cardSupply.buyCard("Bitcoin"));
        }
        for (int i = 0; i < 3; i++) {
            player1.addCardToDeck(cardSupply.buyCard("Method"));
            player2.addCardToDeck(cardSupply.buyCard("Method"));
        }
        player1.shuffleDeck();
        player2.shuffleDeck();
        // Draw initial hands of 5 cards
        player1.drawCards(5);
        player2.drawCards(5);
        System.out.println("Each player starts with 7 Bitcoins and 3 Methods.");
        System.out.println("Initial hands drawn (5 cards each).\n");
    }

    public void play() {
        setup();
        int turnNumber = 1;
        Player currentPlayer = rand.nextBoolean() ? player1 : player2; // Randomly choose starting player
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
        System.out.println(currentPlayer.getName() + " goes first!\n");
        System.out.println("=".repeat(60) + "\n");
        while (cardSupply.getCardCount("Framework") > 0) { // Game continues until all Framework cards are purchased
            System.out.println("Turn " + turnNumber + ":");
            System.out.println("-".repeat(60));
            playTurn(currentPlayer);
            // Swap players
            Player temp = currentPlayer;
            currentPlayer = otherPlayer;
            otherPlayer = temp;
            turnNumber++;
            System.out.println();
        }
        endGame();
    }

    private void playTurn(Player player) {
        System.out.println(player.getName() + "'s turn:");
        // Buy phase
        int coins = player.calculateCoins();
        System.out.println("  Hand: " + getHandDescription(player));
        System.out.println("  Available coins: " + coins);
        // AI strat: buy the most expensive card affordable
        Card boughtCard = decidePurchase(player, coins);
        if (boughtCard != null) {
            player.addCardToDiscard(boughtCard);
            System.out.println("  Purchased: " + boughtCard.getName());
        } else {
            System.out.println("  No purchase made.");
        }
        player.discardHand();  // Cleanup phase
        player.drawCards(5);
    }

    private String getHandDescription(Player player) {
        StringBuilder sb = new StringBuilder();
        for (Card card : player.getHand()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(card.getName());
        }
        return sb.toString();
    }

    private Card decidePurchase(Player player, int coins) {
        String[] priorities = {"Framework", "Module", "Dogecoin", "Method", "Ethereum", "Bitcoin"};
        for (String cardName : priorities) {
            Card prototype = cardSupply.getCardPrototype(cardName);
            if (cardSupply.isAvailable(cardName) && prototype.getCost() <= coins) {
                return cardSupply.buyCard(cardName);
            }
        }
        return null;
    }

    private void endGame() {
        System.out.println("=".repeat(60));
        System.out.println("GAME OVER - All Framework cards have been purchased!");
        System.out.println("=".repeat(60) + "\n");
        int player1Points = player1.calculateTotalAutomationPoints();
        int player2Points = player2.calculateTotalAutomationPoints();
        System.out.println("Final Scores:");
        System.out.println(player1.getName() + ": " + player1Points + " Automation Points");
        System.out.println(player2.getName() + ": " + player2Points + " Automation Points\n");
        if (player1Points > player2Points) {
            System.out.println(player1.getName() + " wins!");
        } else if (player2Points > player1Points) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Supply getSupply() {
        return cardSupply;
    }
}

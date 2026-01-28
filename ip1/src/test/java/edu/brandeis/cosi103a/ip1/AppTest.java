package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AppTest {

    private Supply supply;
    private Player player;

    @Before
    public void setUp() {
        supply = new Supply();
        player = new Player("Test Player");
    }

    // Card Tests
    @Test
    public void testAutomationCardProperties() {
        AutomationCard method = new AutomationCard("Method", 2, 1);
        assertEquals("Method", method.getName());
        assertEquals(2, method.getCost());
        assertEquals(1, method.getValue());
        assertTrue(method.isAutomationCard());
    }

    @Test
    public void testCryptocurrencyCardProperties() {
        CryptocurrencyCard bitcoin = new CryptocurrencyCard("Bitcoin", 0, 1);
        assertEquals("Bitcoin", bitcoin.getName());
        assertEquals(0, bitcoin.getCost());
        assertEquals(1, bitcoin.getValue());
        assertFalse(bitcoin.isAutomationCard());
    }

    // Supply Tests
    @Test
    public void testSupplyInitialCounts() {
        assertEquals(14, supply.getCardCount("Method"));
        assertEquals(8, supply.getCardCount("Module"));
        assertEquals(8, supply.getCardCount("Framework"));
        assertEquals(60, supply.getCardCount("Bitcoin"));
        assertEquals(40, supply.getCardCount("Ethereum"));
        assertEquals(30, supply.getCardCount("Dogecoin"));
    }

    @Test
    public void testSupplyBuyCard() {
        int initialCount = supply.getCardCount("Method");
        Card card = supply.buyCard("Method");
        assertNotNull(card);
        assertEquals("Method", card.getName());
        assertEquals(initialCount - 1, supply.getCardCount("Method"));
    }

    @Test
    public void testSupplyBuyMultipleCards() {
        supply.buyCard("Framework");
        supply.buyCard("Framework");
        supply.buyCard("Framework");
        assertEquals(5, supply.getCardCount("Framework"));
    }

    @Test
    public void testSupplyBuyAllCards() {
        for (int i = 0; i < 8; i++) {
            supply.buyCard("Framework");
        }
        assertEquals(0, supply.getCardCount("Framework"));
        assertNull(supply.buyCard("Framework"));
    }

    @Test
    public void testSupplyIsAvailable() {
        assertTrue(supply.isAvailable("Method"));
        for (int i = 0; i < 14; i++) {
            supply.buyCard("Method");
        }
        assertFalse(supply.isAvailable("Method"));
    }

    // Player Tests
    @Test
    public void testPlayerInitialization() {
        assertEquals("Test Player", player.getName());
        assertEquals(0, player.getHandSize());
        assertEquals(0, player.getDeckSize());
    }

    @Test
    public void testPlayerAddCardToDeck() {
        Card bitcoin = new CryptocurrencyCard("Bitcoin", 0, 1);
        player.addCardToDeck(bitcoin);
        assertEquals(1, player.getDeckSize());
    }

    @Test
    public void testPlayerDrawCards() {
        for (int i = 0; i < 5; i++) {
            player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        player.drawCards(3);
        assertEquals(3, player.getHandSize());
        assertEquals(2, player.getDeckSize());
    }

    @Test
    public void testPlayerDrawWithReshuffle() {
        // Add 7 cards to deck
        for (int i = 0; i < 7; i++) {
            player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        // Draw 5, discard them
        player.drawCards(5);
        player.discardHand();
        // Now deck has 2, discard has 5
        // Draw 5 should trigger reshuffle
        player.drawCards(5);
        assertEquals(5, player.getHandSize());
    }

    @Test
    public void testPlayerCalculateCoins() {
        player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        player.addCardToDeck(new CryptocurrencyCard("Ethereum", 3, 2));
        player.addCardToDeck(new AutomationCard("Method", 2, 1));
        player.drawCards(4);
        assertEquals(4, player.calculateCoins()); // 2 Bitcoins (1 each) + 1 Ethereum (2) = 4 coins, Method card doesn't count
    }

    @Test
    public void testPlayerCalculateCoinsWithDogecoin() {
        player.addCardToDeck(new CryptocurrencyCard("Dogecoin", 6, 3));
        player.addCardToDeck(new CryptocurrencyCard("Ethereum", 3, 2));
        player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        player.drawCards(3);
        assertEquals(6, player.calculateCoins()); // Dogecoin (3) + Ethereum (2) + Bitcoin (1) = 6 coins
    }

    @Test
    public void testPlayerCalculateAutomationPoints() {
        player.addCardToDeck(new AutomationCard("Method", 2, 1));
        player.addCardToDeck(new AutomationCard("Module", 5, 3));
        player.addCardToDeck(new AutomationCard("Framework", 8, 6));
        player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        assertEquals(10, player.calculateTotalAutomationPoints()); // Method (1) + Module (3) + Framework (6) = 10 points, Bitcoin card doesn't count
    }

    @Test
    public void testPlayerCalculateAutomationPointsAcrossPiles() {
        // Add cards to deck
        player.addCardToDeck(new AutomationCard("Method", 2, 1));
        player.addCardToDeck(new AutomationCard("Module", 5, 3));
        player.drawCards(1); // Draw one to hand
        player.addCardToDiscard(new AutomationCard("Framework", 8, 6)); // Add one to discard
        assertEquals(10, player.calculateTotalAutomationPoints()); // Should count all: 1 + 3 + 6 = 10
    }

    @Test
    public void testPlayerDiscardHand() {
        player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        player.drawCards(3);
        assertEquals(3, player.getHandSize());
        player.discardHand();
        assertEquals(0, player.getHandSize());
        assertEquals(3, player.getDiscardPile().size());
    }

    // Game Tests
    @Test
    public void testGameInitialization() {
        Game game = new Game();
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
        assertNotNull(game.getSupply());
    }

    @Test
    public void testGameSetupStarterDecks() {
        Game game = new Game();
        game.setup();
        // Each player should have 10 cards total (7 Bitcoin + 3 Method)
        Player p1 = game.getPlayer1();
        int p1Total = p1.getDeck().size() + p1.getHand().size() + p1.getDiscardPile().size();
        assertEquals(10, p1Total);
        Player p2 = game.getPlayer2();
        int p2Total = p2.getDeck().size() + p2.getHand().size() + p2.getDiscardPile().size();
        assertEquals(10, p2Total);
    }

    @Test
    public void testGameSetupInitialHands() {
        Game game = new Game();
        game.setup();
        // Each player should have 5 cards in hand
        assertEquals(5, game.getPlayer1().getHandSize());
        assertEquals(5, game.getPlayer2().getHandSize());
    }

    @Test
    public void testGameSetupSupplyReduction() {
        Supply supplyBefore = new Supply();
        int bitcoinBefore = supplyBefore.getCardCount("Bitcoin");
        int methodBefore = supplyBefore.getCardCount("Method");
        Game game = new Game();
        game.setup();
        Supply supplyAfter = game.getSupply();
        assertEquals(bitcoinBefore - 14, supplyAfter.getCardCount("Bitcoin")); // 2 players × 7 Bitcoins = 14 Bitcoins taken
        assertEquals(methodBefore - 6, supplyAfter.getCardCount("Method")); // 2 players × 3 Methods = 6 Methods taken
    }

    @Test
    public void testEmptyHandCalculateCoins() {
        assertEquals(0, player.calculateCoins());
    }

    @Test
    public void testEmptyDeckCalculateAutomationPoints() {
        assertEquals(0, player.calculateTotalAutomationPoints());
    }

    @Test
    public void testSupplyGetCardPrototype() {
        Card prototype = supply.getCardPrototype("Framework");
        assertNotNull(prototype);
        assertEquals("Framework", prototype.getName());
        assertEquals(8, prototype.getCost());
        assertEquals(6, prototype.getValue());
    }
}

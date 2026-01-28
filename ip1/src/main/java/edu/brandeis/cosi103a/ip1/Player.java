package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private String name;
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> discardPile;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void addCardToDiscard(Card card) {
        discardPile.add(card);
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            if (deck.isEmpty()) {
                if (discardPile.isEmpty()) {
                    break; // No more cards to draw
                }
                deck.addAll(discardPile); // Shuffle discard pile and make it the new deck
                discardPile.clear();
                shuffleDeck();
            }
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0));
            }
        }
    }

    public void discardHand() {
        discardPile.addAll(hand);
        hand.clear();
    }

    public int calculateCoins() {
        int coins = 0;
        for (Card card : hand) {
            if (!card.isAutomationCard()) {
                coins += card.getValue();
            }
        }
        return coins;
    }

    public int calculateTotalAutomationPoints() {
        int points = 0;
        // Count all cards in deck, hand, and discard pile
        for (Card card : deck) {
            if (card.isAutomationCard()) {
                points += card.getValue();
            }
        }
        for (Card card : hand) {
            if (card.isAutomationCard()) {
                points += card.getValue();
            }
        }
        for (Card card : discardPile) {
            if (card.isAutomationCard()) {
                points += card.getValue();
            }
        }
        return points;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public int getDeckSize() {
        return deck.size();
    }

    public int getHandSize() {
        return hand.size();
    }
}

package edu.brandeis.cosi103a.ip1;

import java.util.HashMap;
import java.util.Map;

public class Supply {
    private Map<String, Integer> cardCounts;
    private Map<String, Card> cardPrototypes;

    public Supply() {
        cardCounts = new HashMap<>();
        cardPrototypes = new HashMap<>();
        // Initialize automation cards
        cardPrototypes.put("Method", new AutomationCard("Method", 2, 1));
        cardCounts.put("Method", 14);
        cardPrototypes.put("Module", new AutomationCard("Module", 5, 3));
        cardCounts.put("Module", 8);
        cardPrototypes.put("Framework", new AutomationCard("Framework", 8, 6));
        cardCounts.put("Framework", 8);
        // Initialize cryptocurrency cards
        cardPrototypes.put("Bitcoin", new CryptocurrencyCard("Bitcoin", 0, 1));
        cardCounts.put("Bitcoin", 60);
        cardPrototypes.put("Ethereum", new CryptocurrencyCard("Ethereum", 3, 2));
        cardCounts.put("Ethereum", 40);
        cardPrototypes.put("Dogecoin", new CryptocurrencyCard("Dogecoin", 6, 3));
        cardCounts.put("Dogecoin", 30);
    }

    public Card buyCard(String cardName) {
        if (cardCounts.containsKey(cardName) && cardCounts.get(cardName) > 0) {
            cardCounts.put(cardName, cardCounts.get(cardName) - 1);
            Card prototype = cardPrototypes.get(cardName);
            if (prototype.isAutomationCard()) {
                return new AutomationCard(prototype.getName(), prototype.getCost(), prototype.getValue());
            } else {
                return new CryptocurrencyCard(prototype.getName(), prototype.getCost(), prototype.getValue());
            }
        }
        return null;
    }

    public int getCardCount(String cardName) {
        return cardCounts.getOrDefault(cardName, 0);
    }

    public Card getCardPrototype(String cardName) {
        return cardPrototypes.get(cardName);
    }

    public boolean isAvailable(String cardName) {
        return cardCounts.containsKey(cardName) && cardCounts.get(cardName) > 0;
    }

    public Map<String, Integer> getCardCounts() {
        return new HashMap<>(cardCounts);
    }
}

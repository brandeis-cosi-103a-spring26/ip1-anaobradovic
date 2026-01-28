package edu.brandeis.cosi103a.ip1;

public abstract class Card {
    protected String name;
    protected int cost;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public abstract int getValue();
    public abstract boolean isAutomationCard();
}

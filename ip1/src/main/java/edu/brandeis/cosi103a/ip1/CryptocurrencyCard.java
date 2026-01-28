package edu.brandeis.cosi103a.ip1;

public class CryptocurrencyCard extends Card {
    private int coinValue;

    public CryptocurrencyCard(String name, int cost, int coinValue) {
        super(name, cost);
        this.coinValue = coinValue;
    }

    @Override
    public int getValue() {
        return coinValue;
    }

    @Override
    public boolean isAutomationCard() {
        return false;
    }
}

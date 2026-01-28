package edu.brandeis.cosi103a.ip1;

public class AutomationCard extends Card {
    private int automationPoints;

    public AutomationCard(String name, int cost, int automationPoints) {
        super(name, cost);
        this.automationPoints = automationPoints;
    }

    @Override
    public int getValue() {
        return automationPoints;
    }

    @Override
    public boolean isAutomationCard() {
        return true;
    }
}
